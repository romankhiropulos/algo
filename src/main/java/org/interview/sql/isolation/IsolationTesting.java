package org.interview.sql.isolation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.interview.hibernate.lock.Person;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class IsolationTesting {
    private final TransactionWrapperIsolation transactionWrapper;

    public void testingLostUpdate() {

        // Сохраним нового пользователя и выведем начальный возраст персоны
        Long idPerson = (Long) transactionWrapper.tx(session -> session.save(Person.builder()
                .name("Mr. White")
                .age(0)
                .build()));
        List<Person> personList = transactionWrapper.tx(session -> session.createQuery(
                String.format("SELECT p FROM Person p WHERE p.id = %d", idPerson), Person.class
        ).list());
        log.info("Current age: " + personList.get(0).getAge());

        // Запускаем нити для инкремента возраста персона
        log.info("Старт нитей:");
        for (int i = 0; i < 100000; i++) {
            new Thread(getAgeUpdatingRunnable(idPerson)).start();
        }
        log.info("Все нити запущены!");

        // Подождем, чтобы все запущенные нити точно завершились по времени
        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }

        // Выведем полученный результат
        personList = transactionWrapper.tx(session -> session.createQuery(
                String.format("SELECT p FROM Person p WHERE p.id = %d", idPerson), Person.class
        ).list());
        log.info("Current age: " + personList.get(0).getAge());
    }

    private Runnable getAgeUpdatingRunnable(Long currentPersonId) {
        return () -> {
            try {
                Thread.sleep(5000);
                transactionWrapper.tu(
                        session -> {
                            NativeQuery nativeQuery = session.createNativeQuery(
                                    String.format("UPDATE person SET age = age + 1 WHERE id = %d", currentPersonId)
                            );
                            nativeQuery.executeUpdate();
                        }
                        ,
                        Connection.TRANSACTION_READ_UNCOMMITTED
                );
            } catch (Exception ex) {
                log.info(ex.getMessage());
            }
        };
    }
}
