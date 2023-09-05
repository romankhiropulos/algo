package org.interview.hibernate.inheritance_strategies.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Book")
@DiscriminatorValue("Book")
public class BookST extends PublicationST {

    @Column
    private int pages;
}
