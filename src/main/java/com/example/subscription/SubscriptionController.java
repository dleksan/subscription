package com.example.subscription;

import org.ldaptive.LdapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NamingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="ntsctsf-time-sync/v1")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController (SubscriptionService subscriptionService){this.subscriptionService=subscriptionService;}

    @PostMapping("subscriptions")
    public void postSubscriptions(@RequestBody Subscription subscription)
    {
        subscriptionService.addNewSubscription(subscription);
    }


    @GetMapping("subscriptions/{subscriptionId}")
    public ResponseEntity<Subscription> getSubscription(
            @PathVariable (value="subscriptionId") String subscriptionId,
            @RequestParam(name="subscribedEvent",required = false) String subscribedEvent,
            @RequestParam(name="snssai",required = false) String snssai

    ) throws NamingException, LdapException, javax.naming.NamingException {

        return subscriptionService.getSubscription(subscriptionId, subscribedEvent, snssai);
    }


    @DeleteMapping("subscriptions/{subscriptionId}")
    public ResponseEntity deleteSubscription(@PathVariable(value = "subscriptionId", required = true) String subscriptionId)
    {
        return subscriptionService.deleteSubscription(subscriptionId);
    }





}
