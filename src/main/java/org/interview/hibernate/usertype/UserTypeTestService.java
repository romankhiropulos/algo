package org.interview.hibernate.usertype;

import org.interview.hibernate.TransactionWrapper;
import org.interview.hibernate.lock.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTypeTestService {
    private final TransactionWrapper transactionWrapper;
    private static final String TEST_PERSON_NAME = "Mr. White";

    public UserTypeTestService(TransactionWrapper transactionWrapper) {
        this.transactionWrapper = transactionWrapper;
    }

    public void testUserType() {
        transactionWrapper.tu(session -> {

                    Person person = Person.builder()
                            .age(29)
                            .name(TEST_PERSON_NAME)
                            .networks(new ArrayList<>())
                            .build();

                    Network network = new Network();
                    NetworkObject networkObject = new NetworkObject("93.184.216.34", (short) 24);
                    network.setNetwork(networkObject);

                    person.addNetwork(network);

                    System.out.println("Сохраняем в базу данных новую сущность people\n");
                    session.save(person);
                }
        );
        transactionWrapper.tu(session -> {
            List<Person> people = session.createQuery(
                            "select distinct p from Person p where p.name = :name", Person.class
                    )
                    .setParameter("name", TEST_PERSON_NAME)
                    .list();
            Person person = people.get(0);
            System.out.println("Выводим в консоль сущность people, взятую из базы данных:\n");
            System.out.println(person);
        });
    }
}
