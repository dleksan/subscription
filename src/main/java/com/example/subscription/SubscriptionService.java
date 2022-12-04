package com.example.subscription;


import org.apache.catalina.connector.Response;
import org.ldaptive.*;
import org.ldaptive.beans.reflect.DefaultLdapEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Name;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;




    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository=subscriptionRepository;


    }
    private Instant parseDateTime(String date){
        return Instant.parse(date);
    }

    private Instant getCurrentDateTime(){
        return Instant.now();
    }
    public Boolean hasSubscriptionExpired(Subscription subscription)
    {
        if(parseDateTime(subscription.getExpiry()).isBefore(getCurrentDateTime()))
        {
            return true;
        }

        return false;

    }

    public ResponseEntity<Subscription> addNewSubscription(Subscription subscription)
    {

        System.out.println(checkMandatoryAttributesForSubscription(subscription));
        if(checkMandatoryAttributesForSubscription(subscription).equals("false"))
        {
            return new ResponseEntity("502", HttpStatus.BAD_REQUEST);
        }

        if(!hasSubscriptionExpired(subscription))
        {
            return new ResponseEntity("502 EXPIRY_NOT_VALID",HttpStatus.BAD_REQUEST);

        }




            Integer Id;
            Random random=new Random();
            Id=random.nextInt(200,300);

            return subscriptionRepository.addSubscription(subscription,Id.toString());





    }

    private String checkMandatoryAttributesForSubscription(Subscription subscription){
        if(subscription.getSupis()==null||
                subscription.getSnssai()==null||
                subscription.getDnn()==null||
                subscription.getEventFilters()==null){
            return "false";
        }
        else{
            return "true";
        }
    }


    public ResponseEntity<Subscription> getSubscription(String subscriptionId, String subscribedEvent, String snssai)  throws NamingException, LdapException {

        ResponseEntity<Subscription> response= subscriptionRepository.getSubscription(subscriptionId);
        Subscription subscription=response.getBody();



        if(hasSubscriptionExpired(subscription))
        {
            subscriptionRepository.deleteSubscription(subscriptionId);
            return new ResponseEntity("403 FORBIDDEN", HttpStatus.FORBIDDEN);
        }

        if(snssai!=null && subscription.getSnssai()!=null){

            List<String> snssaiAttributes= List.of(snssai.split("-"));
            String sst=snssaiAttributes.get(0);
            String sd=snssaiAttributes.get(1);

            if(!(sst.equals(subscription.getSnssai().getSst().toString()) && sd.equals(subscription.getSnssai().getSd())))
            {
                return new ResponseEntity("403 SNSSAI_NOT_ALLOWED",HttpStatus.FORBIDDEN);
            }
        }

        if(subscription.getSubscribedEvents()!=null && subscribedEvent!=null)
        {
            if(!(subscription.getSubscribedEvents().contains(subscribedEvent)))
            {
                return new ResponseEntity("EVENT_NOT_ALLOWED", HttpStatus.FORBIDDEN);
            }
        }

        return response;
    }





    public ResponseEntity deleteSubscription(String subscriptionId)
    {
        subscriptionRepository.getSubscription(subscriptionId);
        return subscriptionRepository.deleteSubscription(subscriptionId);
    }






}
