package model;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Events;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-29T11:10:50")
@StaticMetamodel(EventRecovery.class)
public class EventRecovery_ { 

    public static volatile SingularAttribute<EventRecovery, Long> eventid;
    public static volatile SingularAttribute<EventRecovery, Events> rEventid;
    public static volatile SingularAttribute<EventRecovery, Events> cEventid;
    public static volatile SingularAttribute<EventRecovery, BigInteger> correlationid;
    public static volatile SingularAttribute<EventRecovery, BigInteger> userid;
    public static volatile SingularAttribute<EventRecovery, Events> events;

}