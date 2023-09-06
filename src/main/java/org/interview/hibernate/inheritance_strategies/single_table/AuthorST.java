package org.interview.hibernate.inheritance_strategies.single_table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "author_st")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorST {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private Integer version;
}
