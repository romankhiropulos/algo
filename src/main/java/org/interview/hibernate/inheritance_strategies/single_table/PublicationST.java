package org.interview.hibernate.inheritance_strategies.single_table;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Publication_Type")
public class PublicationST {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Column
    protected String title;

    @Version
    @Column(name = "version")
    private int version;

    @ManyToMany
    @JoinTable(
            name = "PublicationAuthor",
            joinColumns = {
                    @JoinColumn(name = "publicationId", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authorId", referencedColumnName = "id")
            })
    private Set<AuthorST> authors = new HashSet<>();

    @Column
    @Temporal(TemporalType.DATE)
    private Date publishingDate;
}
