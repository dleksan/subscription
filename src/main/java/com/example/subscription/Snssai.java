package com.example.subscription;


import org.ldaptive.beans.Attribute;
import org.ldaptive.beans.Entry;


@Entry(
        dn = "cn",
        attributes = {
                @Attribute(name="objectClass", values={"snssai"}),
                @Attribute(name = "sst", property = "sst"),
                @Attribute(name = "sd", property = "sd"),
        })

public class Snssai {

    private String cn;
    private Integer sst;
    private  String sd;

    public Snssai(String cn, Integer sst, String sd) {
        this.cn = cn;
        this.sst = sst;
        this.sd = sd;
    }

    public Snssai() {

    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public Integer getSst() {
        return sst;
    }



    public void setSst(Integer sst) {
        this.sst = sst;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }


}
