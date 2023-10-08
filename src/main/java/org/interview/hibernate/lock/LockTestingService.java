package org.interview.hibernate.lock;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.LockMode;
import org.interview.hibernate.TransactionWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LockTestingService {

    private static final String TEST_PERSON_NAME = "Mr. White";
    private final TransactionWrapper transactionWrapper;

    public LockTestingService(TransactionWrapper transactionWrapper) {
        this.transactionWrapper = transactionWrapper;
    }

    public void testOptimisticLock() {
        transactionWrapper.tu(session -> session.save(
                Person.builder()
                        .age(29)
                        .name(TEST_PERSON_NAME)
                        .build()
        ));
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                transactionWrapper.tu(session -> {
                    List<Person> people = session.createQuery(
                                    "select distinct p from Person p where p.name = :name", Person.class
                            )
                            .setParameter("name", TEST_PERSON_NAME)
                            .list();
                    Person person = people.get(0);
                    System.out.println("Прочитали объект person с id " + person.getId() + " с базы данных второй транзакцией ");
                    System.out.println("Текущая версия person " + person.getVersion());
                    person.setAge(38);
                    System.out.println("Поменяли возраст оптимистично заблокированного person");
                    System.out.println("Текущая версия person " + person.getVersion());
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        transactionWrapper.tu(session -> {
            System.out.println("Читаем объект person с базы данных первой транзакцией ");
            List<Person> people = session.createQuery(
                            "select distinct p from Person p where p.name = :name", Person.class
                    )
                    .setParameter("name", TEST_PERSON_NAME)
                    .list();
            Person person = people.get(0);
            System.out.println("Текущая версия person " + person.getVersion());
            session.lock(person, LockMode.OPTIMISTIC);
            System.out.println("Заблокировали person с id " + person.getId() + " и версией " + person.getVersion());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Перед выходом из первой транзакции: person с id " + person.getId() + " и версией " + person.getVersion());
            System.out.println("Коммит изменений и выход из первой транзакции, где оптимистично заблокировали person");
        });
    }
}
