package com.corpse.configuration;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public SessionFactory sessionFactory() {
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		if(sessionFactory == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return sessionFactory;
	}
}
