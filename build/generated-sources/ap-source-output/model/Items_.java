package model;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Hosts;
import model.Interface;
import model.Items;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-26T16:14:46")
@StaticMetamodel(Items.class)
public class Items_ { 

    public static volatile SingularAttribute<Items, Integer> flags;
    public static volatile SingularAttribute<Items, String> lifetime;
    public static volatile SingularAttribute<Items, String> description;
    public static volatile SingularAttribute<Items, Hosts> hostid;
    public static volatile SingularAttribute<Items, String> units;
    public static volatile SingularAttribute<Items, Integer> type;
    public static volatile SingularAttribute<Items, String> error;
    public static volatile SingularAttribute<Items, Integer> mtime;
    public static volatile SingularAttribute<Items, Integer> snmpv3Securitylevel;
    public static volatile SingularAttribute<Items, Integer> authtype;
    public static volatile SingularAttribute<Items, Long> itemid;
    public static volatile SingularAttribute<Items, String> privatekey;
    public static volatile SingularAttribute<Items, String> password;
    public static volatile SingularAttribute<Items, Integer> valueType;
    public static volatile SingularAttribute<Items, String> snmpv3Authpassphrase;
    public static volatile SingularAttribute<Items, Integer> state;
    public static volatile SingularAttribute<Items, Interface> interfaceid;
    public static volatile SingularAttribute<Items, String> key;
    public static volatile SingularAttribute<Items, String> trends;
    public static volatile SingularAttribute<Items, String> snmpv3Securityname;
    public static volatile SingularAttribute<Items, String> ipmiSensor;
    public static volatile SingularAttribute<Items, BigInteger> valuemapid;
    public static volatile SingularAttribute<Items, String> snmpCommunity;
    public static volatile SingularAttribute<Items, Integer> inventoryLink;
    public static volatile SingularAttribute<Items, String> jmxEndpoint;
    public static volatile SingularAttribute<Items, Integer> snmpv3Privprotocol;
    public static volatile SingularAttribute<Items, String> publickey;
    public static volatile SingularAttribute<Items, String> history;
    public static volatile SingularAttribute<Items, String> logtimefmt;
    public static volatile SingularAttribute<Items, String> params;
    public static volatile SingularAttribute<Items, Items> templateid;
    public static volatile SingularAttribute<Items, Long> lastlogsize;
    public static volatile SingularAttribute<Items, Integer> snmpv3Authprotocol;
    public static volatile SingularAttribute<Items, String> snmpv3Contextname;
    public static volatile SingularAttribute<Items, Integer> evaltype;
    public static volatile SingularAttribute<Items, String> delay;
    public static volatile SingularAttribute<Items, String> port;
    public static volatile SingularAttribute<Items, String> name;
    public static volatile SingularAttribute<Items, String> snmpOid;
    public static volatile SingularAttribute<Items, String> formula;
    public static volatile SingularAttribute<Items, String> trapperHosts;
    public static volatile SingularAttribute<Items, Items> masterItemid;
    public static volatile SingularAttribute<Items, String> snmpv3Privpassphrase;
    public static volatile SingularAttribute<Items, Integer> status;
    public static volatile SingularAttribute<Items, String> username;

}