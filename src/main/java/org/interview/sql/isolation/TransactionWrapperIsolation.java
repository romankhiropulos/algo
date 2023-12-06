package org.interview.sql.isolation;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
public class TransactionWrapperIsolation {

    private final SessionFactory sf;

//    @PersistenceContext
//    private EntityManager entityManager;

    public TransactionWrapperIsolation(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sf = entityManagerFactory.unwrap(SessionFactory.class);
    }
    public void tu(final Consumer<Session> command, int transactionIsolationLevel) {
        final Session session = sf.openSession();
        // final Session session = (Session) entityManager.getDelegate();
        final Transaction tx = session.beginTransaction();
        try {
            session.doWork(connection -> {
                connection.setTransactionIsolation(transactionIsolationLevel);
//                log.info("Transaction isolation level is {}",
//                         ConnectionProviderInitiator.toIsolationNiceName(connection.getTransactionIsolation()));
                command.accept(session);
            });
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void tu(final Consumer<Session> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public <T> T tx(final Function<Session, T> command) {
         final Session session = sf.openSession();
        //final Session session = (Session) entityManager.getDelegate();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
