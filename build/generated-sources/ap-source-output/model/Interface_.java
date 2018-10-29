package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Hosts;
import model.Items;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-29T11:10:50")
@StaticMetamodel(Interface.class)
public class Interface_ { 

    public static volatile SingularAttribute<Interface, String> port;
    public static volatile ListAttribute<Interface, Items> itemsList;
    public static volatile SingularAttribute<Interface, String> ip;
    public static volatile SingularAttribute<Interface, Integer> useip;
    public static volatile SingularAttribute<Interface, String> dns;
    public static volatile SingularAttribute<Interface, Hosts> hostid;
    public static volatile SingularAttribute<Interface, Integer> main;
    public static volatile SingularAttribute<Interface, Long> interfaceid;
    public static volatile SingularAttribute<Interface, Integer> type;
    public static volatile SingularAttribute<Interface, Integer> bulk;

}