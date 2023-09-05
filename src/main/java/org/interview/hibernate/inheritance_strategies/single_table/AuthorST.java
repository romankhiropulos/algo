package org.interview.hibernate.inheritance_strategies.single_table;

import javax.persistence.*;

@Entity
public class AuthorST {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private Integer version;
}
