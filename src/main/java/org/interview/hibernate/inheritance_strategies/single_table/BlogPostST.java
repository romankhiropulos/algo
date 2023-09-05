package org.interview.hibernate.inheritance_strategies.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "BlogPost")
@DiscriminatorValue("Blog")
public class BlogPostST extends PublicationST {

    @Column
    private String url;
}
