package org.interview.hibernate.lock;

import lombok.*;
import org.interview.hibernate.usertype.Network;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    private Long version;

    private Integer age;

    private String name;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Network> networks = new ArrayList<>();

    public void addNetwork(Network network) {
        this.networks.add(network);
        network.setPerson(this);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("version=" + version)
                .add("age=" + age)
                .add("name='" + name + "'")
                .add("networks=" + networks)
                .toString();
    }
}
