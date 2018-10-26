package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Triggers;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-26T16:14:46")
@StaticMetamodel(Triggers.class)
public class Triggers_ { 

    public static volatile SingularAttribute<Triggers, String> expression;
    public static volatile SingularAttribute<Triggers, String> comments;
    public static volatile SingularAttribute<Triggers, Long> triggerid;
    public static volatile SingularAttribute<Triggers, Integer> recoveryMode;
    public static volatile SingularAttribute<Triggers, Integer> flags;
    public static volatile SingularAttribute<Triggers, String> description;
    public static volatile SingularAttribute<Triggers, Integer> priority;
    public static volatile SingularAttribute<Triggers, String> error;
    public static volatile SingularAttribute<Triggers, Integer> type;
    public static volatile SingularAttribute<Triggers, Triggers> templateid;
    public static volatile SingularAttribute<Triggers, String> url;
    public static volatile SingularAttribute<Triggers, Integer> correlationMode;
    public static volatile SingularAttribute<Triggers, String> recoveryExpression;
    public static volatile SingularAttribute<Triggers, String> correlationTag;
    public static volatile SingularAttribute<Triggers, Integer> state;
    public static volatile SingularAttribute<Triggers, Integer> lastchange;
    public static volatile SingularAttribute<Triggers, Integer> value;
    public static volatile SingularAttribute<Triggers, Integer> manualClose;
    public static volatile SingularAttribute<Triggers, Integer> status;

}