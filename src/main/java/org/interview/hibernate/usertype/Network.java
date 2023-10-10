package org.interview.hibernate.usertype;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.interview.hibernate.lock.Person;

import javax.persistence.*;
import java.util.StringJoiner;

/**
 * Network storing entity.
 */
@Entity
@Getter
@Setter
public class Network {

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * Network data.
     */
    @Type(type = "org.interview.hibernate.usertype.NetworkObjectType")
    private NetworkObject network;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public String toString() {
        return new StringJoiner(", ", Network.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("network=" + network)
                .toString();
    }
}
