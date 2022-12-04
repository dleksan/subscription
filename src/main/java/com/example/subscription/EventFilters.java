package com.example.subscription;

import org.ldaptive.beans.Attribute;
import org.ldaptive.beans.Entry;

import java.util.List;


@Entry(
        dn = "eventFilterId",
        attributes = {
                @Attribute(name="objectClass", values={"eventFilters"}),
                @Attribute(name = "instanceTypes", property = "instanceTypes"),
                @Attribute(name = "transProtocols", property = "transProtocols"),
                @Attribute(name = "ptpProfiles", property = "ptpProfiles"),

        })

public class EventFilters {

    private String eventFilterId;
   List<String> instanceTypes;
    List<String> transProtocols;
    List<String> ptpProfiles;

    public EventFilters() {
        this.eventFilterId = eventFilterId;
        this.instanceTypes = instanceTypes;
        this.transProtocols = transProtocols;
        this.ptpProfiles = ptpProfiles;
    }


    public String getEventFilterId() {
        return eventFilterId;
    }

    public void setEventFilterId(String eventFilterId) {
        this.eventFilterId = eventFilterId;
    }

    public List<String> getInstanceTypes() {
        return instanceTypes;
    }

    public void setInstanceTypes(List<String> instanceTypes) {
        this.instanceTypes = instanceTypes;
    }

    public List<String> getTransProtocols() {
        return transProtocols;
    }

    public void setTransProtocols(List<String> transProtocols) {
        this.transProtocols = transProtocols;
    }

    public List<String> getPtpProfiles() {
        return ptpProfiles;
    }

    public void setPtpProfiles(List<String> ptpProfiles) {
        this.ptpProfiles = ptpProfiles;
    }


}
