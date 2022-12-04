package com.example.subscription;

import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.beans.reflect.DefaultLdapEntryMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Hashtable;
import java.util.List;

@SpringBootApplication
public class SubscriptionApplication {




	public static void main(String[] args) throws LdapException, NamingException {

		SpringApplication.run(SubscriptionApplication.class, args);




	}





}
