package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.EventRecovery;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-29T11:10:50")
@StaticMetamodel(Events.class)
public class Events_ { 

    public static volatile SingularAttribute<Events, Long> eventid;
    public static volatile SingularAttribute<Events, Integer> acknowledged;
    public static volatile SingularAttribute<Events, Integer> ns;
    public static volatile SingularAttribute<Events, EventRecovery> eventRecovery;
    public static volatile SingularAttribute<Events, Integer> source;
    public static volatile SingularAttribute<Events, Integer> clock;
    public static volatile SingularAttribute<Events, Integer> value;
    public static volatile SingularAttribute<Events, Long> objectid;
    public static volatile SingularAttribute<Events, Integer> object;

}