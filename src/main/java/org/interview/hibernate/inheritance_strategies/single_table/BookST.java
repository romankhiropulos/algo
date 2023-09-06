package org.interview.hibernate.inheritance_strategies.single_table;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "BookST")
@Table(schema = "public", name = "book_st")
@DiscriminatorValue("Book")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookST extends PublicationST {

    @Column
    private int pages;
}
