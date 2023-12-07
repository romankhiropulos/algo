package org.interview.sql.isolation;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.interview.hibernate.lock.Person;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class TransactionWrapperIsolation {

    @PersistenceContext
    private EntityManager entityManager;

    void saveEntity(Person person) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(person);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    /**
     * Второй способ с помощью которого я получал объект session, не используя напрямую entityManager
     * <p>
     * private final SessionFactory sf;
     * public TransactionWrapperIsolation(EntityManagerFactory entityManagerFactory) {
     * if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
     * throw new NullPointerException("factory is not a hibernate factory");
     * }
     * this.sf = entityManagerFactory.unwrap(SessionFactory.class);
     * }
     * <p>
     * В методе tu|tx получаем session так:
     * final Session session = sf.openSession();
     */

    public void tu(final Consumer<Session> command, int transactionIsolationLevel) {
        final Session session = (Session) entityManager.getDelegate();
        /* В строке выше получается закрытый объект session,
         * поэтому открыл его, вызвав сперва у session его фабрику,
         * а уже у фабрики новый открытый объект session1 */
        final Session session1 = session.getSessionFactory().openSession();
        final Transaction tx = session1.beginTransaction();
        try {
            session1.doWork(connection -> {
                connection.setTransactionIsolation(transactionIsolationLevel);
//                log.info("Transaction isolation level is {}",
//                         ConnectionProviderInitiator.toIsolationNiceName(connection.getTransactionIsolation()));
                command.accept(session1);
            });
            tx.commit();
        } catch (final Exception e) {
            session1.getTransaction().rollback();
            throw e;
        } finally {
            session1.close();
        }

    }

    public <T> T tx(final Function<Session, T> command) {
        Session session = (Session) entityManager.getDelegate();
        Session session1 = session.getSessionFactory().openSession();
        final Transaction tx = session1.beginTransaction();
        try {
            T rsl = command.apply(session1);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session1.getTransaction().rollback();
            throw e;
        } finally {
            session1.close();
        }
    }
}
