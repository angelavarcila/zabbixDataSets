package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Items;
import model.Triggers;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-26T16:14:46")
@StaticMetamodel(Functions.class)
public class Functions_ { 

    public static volatile SingularAttribute<Functions, Items> itemid;
    public static volatile SingularAttribute<Functions, Long> functionid;
    public static volatile SingularAttribute<Functions, String> function;
    public static volatile SingularAttribute<Functions, String> parameter;
    public static volatile SingularAttribute<Functions, Triggers> triggerid;

}