package org.interview.hibernate.inheritance_strategies.single_table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "BlogPostST")
@Table(schema = "public", name = "blog_post_st")
@DiscriminatorValue("Blog")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostST extends PublicationST {

    @Column
    private String url;
}