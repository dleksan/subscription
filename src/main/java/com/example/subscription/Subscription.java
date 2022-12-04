package com.example.subscription;


import org.ldaptive.beans.Attribute;
import org.ldaptive.beans.Entry;

import java.util.ArrayList;
import java.util.List;

@Entry(
        dn = "subscriptionId",
        attributes = {
                @Attribute(name="objectClass", values={"subscription"}),
                @Attribute(name = "supis", property = "supis"),
                @Attribute(name = "interGrpId", property = "interGrpId"),
                @Attribute(name = "anyUeInd", property = "anyUeInd"),
                @Attribute(name = "notifMethod", property = "notifMethod"),
                @Attribute(name = "dnn", property = "dnn"),
                @Attribute(name = "subscribedEvents", property = "subscribedEvents"),
                @Attribute(name = "subsNotifUri", property = "subsNotifUri"),
                @Attribute(name = "subsNotifId", property = "subsNotifId"),
                @Attribute(name = "maxReportNbr", property = "maxReportNbr"),
                @Attribute(name = "expiry", property = "expiry"),
                @Attribute(name = "repPeriod", property = "repPeriod"),
                @Attribute(name = "suppFeat", property = "suppFeat")

        }
)

public class Subscription {

        private String subscriptionId;
        private List<String> supis;
        private String interGrpId;
        private Boolean anyUeInd;
        private String notifMethod;
        private String dnn;
        private List<String> subscribedEvents;
        private String subsNotifUri;
        private String subsNotifId;
        private Integer maxReportNbr;
        private String expiry;
        private Integer repPeriod;
        private String suppFeat;

        private List<EventFilters> eventFilters;
        private Snssai snssai;

    public Subscription(String subscriptionId, List<String> supis, String interGrpId, Boolean anyUeInd, String notifMethod, String dnn, List<String> subscribedEvents, String subsNotifUri, String subsNotifId, Integer maxReportNbr, String expiry, Integer repPeriod, String suppFeat, List<EventFilters> eventFilters, Snssai snssai) {
        this.subscriptionId = subscriptionId;
        this.supis = supis;
        this.interGrpId = interGrpId;
        this.anyUeInd = anyUeInd;
        this.notifMethod = notifMethod;
        this.dnn = dnn;
        this.subscribedEvents = subscribedEvents;
        this.subsNotifUri = subsNotifUri;
        this.subsNotifId = subsNotifId;
        this.maxReportNbr = maxReportNbr;
        this.expiry = expiry;
        this.repPeriod = repPeriod;
        this.suppFeat = suppFeat;
        this.eventFilters = eventFilters;
        this.snssai = snssai;
    }

    public Subscription() {

    }


    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public List<String> getSupis() {
        return supis;
    }

    public void setSupis(List<String> supis) {
        this.supis = supis;
    }

    public String getInterGrpId() {
        return interGrpId;
    }

    public void setInterGrpId(String interGrpId) {
        this.interGrpId = interGrpId;
    }

    public Boolean getAnyUeInd() {
        return anyUeInd;
    }

    public void setAnyUeInd(Boolean anyUeInd) {
        this.anyUeInd = anyUeInd;
    }

    public String getNotifMethod() {
        return notifMethod;
    }

    public void setNotifMethod(String notifMethod) {
        this.notifMethod = notifMethod;
    }

    public String getDnn() {
        return dnn;
    }

    public void setDnn(String dnn) {
        this.dnn = dnn;
    }

    public List<String> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(List<String> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }

    public String getSubsNotifUri() {
        return subsNotifUri;
    }

    public void setSubsNotifUri(String subsNotifUri) {
        this.subsNotifUri = subsNotifUri;
    }

    public String getSubsNotifId() {
        return subsNotifId;
    }

    public void setSubsNotifId(String subsNotifId) {
        this.subsNotifId = subsNotifId;
    }

    public Integer getMaxReportNbr() {
        return maxReportNbr;
    }

    public void setMaxReportNbr(Integer maxReportNbr) {
        this.maxReportNbr = maxReportNbr;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public Integer getRepPeriod() {
        return repPeriod;
    }

    public void setRepPeriod(Integer repPeriod) {
        this.repPeriod = repPeriod;
    }

    public String getSuppFeat() {
        return suppFeat;
    }

    public void setSuppFeat(String suppFeat) {
        this.suppFeat = suppFeat;
    }

    public List<EventFilters> getEventFilters() {
        return eventFilters;
    }

    public Snssai getSnssai() {
        return snssai;
    }

    public void setEventFilters(List<EventFilters> eventFilters) {
        this.eventFilters = eventFilters;
    }



    public void setSnssai(Snssai snssai) {
        this.snssai = snssai;
    }

    public void setEventFilter(EventFilters eventFilter)
    {
        if(this.eventFilters==null){
            this.eventFilters=new ArrayList<EventFilters>();
        }
        this.eventFilters.add(eventFilter);
    }
}



