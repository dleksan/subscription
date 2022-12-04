package com.example.subscription;

import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.ldaptive.*;
import org.ldaptive.beans.reflect.DefaultLdapEntryMapper;
import org.ldaptive.handler.ResultPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.naming.NamingException;
import javax.xml.stream.EventFilter;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

@Repository
public class SubscriptionRepository {


    SingleConnectionFactory connection;

    String ldapUri="ldap://10.14.42.6:389/dc=example,dc=com";
    String baseDn ="dc=example,dc=com";
    String user="cn=admin,dc=example,dc=com";
    String password="secret";

    public void openConnection() throws LdapException {
        SingleConnectionFactory cf = new SingleConnectionFactory(ldapUri);
        cf.initialize();
        BindOperation bind = new BindOperation(cf);
        BindResponse res = bind.execute(SimpleBindRequest.builder()
                .dn(user)
                .password(password)
                .build());
        if (res.isSuccess()) {
            this.connection=cf;

        }

    }

    public void closeConnection() throws NamingException {
        connection.close();

    }

    public ResponseEntity<Subscription> addSubscription(Subscription subscription, String subscriptionId) {

        Integer eventFilterIdCounter=0;

        try {
            openConnection();
            AddOperation add = AddOperation.builder()
                    .factory(connection)
                    .throwIf(ResultPredicate.NOT_SUCCESS)
                    .build();

            //Subscription
            DefaultLdapEntryMapper<Subscription> subscriptionMapper = new DefaultLdapEntryMapper<>();
            LdapEntry subscriptionEntry = new LdapEntry();
            subscriptionMapper.map(subscription, subscriptionEntry);

            add.execute(AddRequest.builder()
                    .dn("subscriptionId=" + subscriptionId + "," + baseDn)
                    .attributes(subscriptionEntry.getAttributes())
                    .build());

            //snssai
            Snssai snssai=subscription.getSnssai();
            DefaultLdapEntryMapper<Snssai> snssaiMapper=new DefaultLdapEntryMapper<>();
            LdapEntry snssaiEntry=new LdapEntry();
            snssaiMapper.map(snssai,snssaiEntry);

            add.execute(AddRequest.builder()
                    .dn("cn=snssai,subscriptionId="+ subscriptionId + "," + baseDn)
                    .attributes(snssaiEntry.getAttributes())
                    .build());

            //eventFilters
            List<EventFilters> eventFilters=subscription.getEventFilters();
            Integer eventFilterCounter=0;

            for(EventFilters filters:eventFilters){
                DefaultLdapEntryMapper<EventFilters> eventFilterMapper=new DefaultLdapEntryMapper<>();
                LdapEntry eventFilterEntry=new LdapEntry();
                eventFilterMapper.map(filters,eventFilterEntry);

                add.execute(AddRequest.builder()
                        .dn("eventFilterId="+eventFilterCounter+",subscriptionId="+ subscriptionId + "," + baseDn)
                        .attributes(eventFilterEntry.getAttributes())
                        .build());
                eventFilterCounter++;
            }

            connection.close();




        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity("400 BAD_REQUEST",HttpStatus.BAD_REQUEST);
        }

        String path= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("ntsctsf-time-sync/v1/subscriptions/"+subscriptionId)
                .build().toUriString();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION,path).body(subscription);

    }

    public ResponseEntity<Subscription> getSubscription(String subscriptionId)
    {
        try{
            openConnection();

            Subscription subscription=new Subscription();
            Snssai snssai = new Snssai();
            EventFilters eventFilters=new EventFilters();


            SearchOperation search=SearchOperation.builder()
                    .factory(connection)
                    .throwIf(ResultPredicate.NOT_SUCCESS)
                    .build();
            SearchResponse response = search
                    .execute(SearchRequest.builder()
                            .dn("subscriptionId=" + subscriptionId + "," + baseDn)
                            .filter("(|(objectClass=subscription)(objectClass=snssai)(objectClass=eventFilters))")
                            .build());

            Collection<LdapEntry> ldapEntries=response.getEntries();

            for(LdapEntry ldapEntry : ldapEntries)
            {
                String objectClass=ldapEntry.getAttribute("objectClass").getStringValue();

                if(objectClass.equals("subscription"))
                {
                    DefaultLdapEntryMapper<Subscription> subscriptionMapper=new DefaultLdapEntryMapper<>();
                    subscriptionMapper.map(ldapEntry,subscription);

                }
                else if (objectClass.equals("snssai"))
                {
                    DefaultLdapEntryMapper<Snssai> snssaiMapper=new DefaultLdapEntryMapper<>();
                    snssaiMapper.map(ldapEntry,snssai);
                    subscription.setSnssai(snssai);
                }

                else if(objectClass.equals("eventFilters"))
                {
                    DefaultLdapEntryMapper<EventFilters> eventFilterMapper=new DefaultLdapEntryMapper<>();
                    eventFilterMapper.map(ldapEntry,eventFilters);
                    subscription.setEventFilter(eventFilters);
                }


            }

            closeConnection();

            return new ResponseEntity(subscription, HttpStatus.OK);


        } catch (LdapException | NamingException e) {

            return new ResponseEntity("BAD_REQUEST", HttpStatus.BAD_REQUEST);

        }


    }


    public ResponseEntity deleteSubscription(String subscriptionId)
    {
        try{
            openConnection();
            Stack<String> dns=new Stack<>();
            SearchOperation searchOperation= SearchOperation.builder()
                    .factory(connection)
                    .throwIf(ResultPredicate.NOT_SUCCESS)
                    .build();

            SearchResponse searchResponse=searchOperation.execute(SearchRequest.builder()
                    .dn("subscriptionId="+subscriptionId+","+baseDn)
                    .filter("(|(objectClass=subscription)(objectClass=snssai)(objectClass=eventFilters))")
                    .build());

            for (LdapEntry ldapEntry:searchResponse.getEntries())
            {
                dns.push(ldapEntry.getDn());
            }

            DeleteOperation deleteOperation=DeleteOperation.builder()
                            .factory(connection)
                            .throwIf(ResultPredicate.NOT_SUCCESS)
                            .build();

            while (!dns.isEmpty())
            {
                String tempDn=dns.pop();

                deleteOperation.execute(DeleteRequest.builder()
                        .dn(tempDn)
                        .build());
            }

            closeConnection();





        } catch (LdapException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity("204 NO_CONTENT", HttpStatus.NO_CONTENT);
    }
}
