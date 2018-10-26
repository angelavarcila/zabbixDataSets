package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.HistoryLogPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-26T16:14:46")
@StaticMetamodel(HistoryLog.class)
public class HistoryLog_ { 

    public static volatile SingularAttribute<HistoryLog, Integer> severity;
    public static volatile SingularAttribute<HistoryLog, Integer> logeventid;
    public static volatile SingularAttribute<HistoryLog, HistoryLogPK> historyLogPK;
    public static volatile SingularAttribute<HistoryLog, Integer> ns;
    public static volatile SingularAttribute<HistoryLog, String> source;
    public static volatile SingularAttribute<HistoryLog, String> value;
    public static volatile SingularAttribute<HistoryLog, Integer> timestamp;

}