package org.interview.hibernate.usertype;

import lombok.Data;

import java.io.Serializable;

/**
 * Describes ip network as a pair of host and mask.
 */
@Data
public class NetworkObject implements Serializable {

    /**
     * Serializable requirement.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Address part of network.
     */
    private final String address;

    /**
     * Bitmask part of the network.
     */
    private final short bitmask;
}
