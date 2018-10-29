package model;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Hosts;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-29T11:10:50")
@StaticMetamodel(Hosts.class)
public class Hosts_ { 

    public static volatile SingularAttribute<Hosts, Integer> ipmiErrorsFrom;
    public static volatile SingularAttribute<Hosts, String> tlsSubject;
    public static volatile SingularAttribute<Hosts, Integer> available;
    public static volatile SingularAttribute<Hosts, Integer> flags;
    public static volatile SingularAttribute<Hosts, Long> hostid;
    public static volatile SingularAttribute<Hosts, String> description;
    public static volatile SingularAttribute<Hosts, String> error;
    public static volatile SingularAttribute<Hosts, Integer> ipmiAuthtype;
    public static volatile SingularAttribute<Hosts, Integer> ipmiPrivilege;
    public static volatile SingularAttribute<Hosts, Integer> snmpAvailable;
    public static volatile SingularAttribute<Hosts, String> snmpError;
    public static volatile SingularAttribute<Hosts, String> tlsPskIdentity;
    public static volatile SingularAttribute<Hosts, Integer> ipmiDisableUntil;
    public static volatile SingularAttribute<Hosts, String> ipmiError;
    public static volatile SingularAttribute<Hosts, BigInteger> maintenanceid;
    public static volatile SingularAttribute<Hosts, Integer> maintenanceStatus;
    public static volatile SingularAttribute<Hosts, String> tlsPsk;
    public static volatile SingularAttribute<Hosts, Integer> tlsConnect;
    public static volatile SingularAttribute<Hosts, String> host;
    public static volatile SingularAttribute<Hosts, Integer> tlsAccept;
    public static volatile SingularAttribute<Hosts, String> ipmiPassword;
    public static volatile SingularAttribute<Hosts, Integer> maintenanceType;
    public static volatile SingularAttribute<Hosts, String> tlsIssuer;
    public static volatile SingularAttribute<Hosts, Integer> jmxErrorsFrom;
    public static volatile SingularAttribute<Hosts, Integer> jmxDisableUntil;
    public static volatile SingularAttribute<Hosts, Hosts> templateid;
    public static volatile SingularAttribute<Hosts, Hosts> proxyHostid;
    public static volatile SingularAttribute<Hosts, Integer> snmpErrorsFrom;
    public static volatile SingularAttribute<Hosts, Integer> ipmiAvailable;
    public static volatile SingularAttribute<Hosts, Integer> lastaccess;
    public static volatile SingularAttribute<Hosts, Integer> maintenanceFrom;
    public static volatile SingularAttribute<Hosts, String> jmxError;
    public static volatile SingularAttribute<Hosts, String> name;
    public static volatile SingularAttribute<Hosts, Integer> disableUntil;
    public static volatile SingularAttribute<Hosts, Integer> jmxAvailable;
    public static volatile SingularAttribute<Hosts, String> ipmiUsername;
    public static volatile SingularAttribute<Hosts, Integer> snmpDisableUntil;
    public static volatile SingularAttribute<Hosts, Integer> errorsFrom;
    public static volatile SingularAttribute<Hosts, Integer> status;

}