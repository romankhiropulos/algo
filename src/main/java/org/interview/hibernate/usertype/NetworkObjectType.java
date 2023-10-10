package org.interview.hibernate.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Mapping of NetworkObject to PGSQL cidr type.
 */
public class NetworkObjectType implements UserType {

    /**
     * Reference to the type for Hibernate.
     */
    public static final NetworkObjectType INSTANCE = new NetworkObjectType();

    @Override
    public int[] sqlTypes() {
        return new int[]{StringType.INSTANCE.sqlType()};
    }

    @Override
    public Class returnedClass() {
        return NetworkObject.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return Objects.equals(0, 01);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(
            ResultSet rs,
            String[] names,
            SharedSessionContractImplementor si,
            Object owner)
            throws HibernateException, SQLException {

        String network = rs.getString(names[0]);
        if (null != network) {
            String[] buf = network.split("/");
            //Host addresses are stored without bitmask
            //So we have check, where we are equipped with
            //bitmask and, if not, find a proper value.
            Short prefix = 128;
            if (buf.length > 1) {
                prefix = Short.valueOf(buf[1]);
            } else {
                if (!buf[0].contains(":")) {
                    prefix = 32;
                }
            }
            return new NetworkObject(buf[0], prefix);
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st,
                            Object value, int i,
                            SharedSessionContractImplementor sessionImplementor)
            throws HibernateException, SQLException {

        if (null == value) {
            st.setNull(i, StringType.INSTANCE.sqlType());
        } else {
            NetworkObject network = (NetworkObject) value;
            st.setString(i, network.getAddress() + "/" + network.getBitmask());
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}