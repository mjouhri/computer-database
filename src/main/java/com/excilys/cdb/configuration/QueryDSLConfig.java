package com.excilys.cdb.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class QueryDSLConfig {
	
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//	      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//	      em.setDataSource(dataSource);
//	      em.setPackagesToScan(new String[] { "fr.excilys.cdb.persistence.models" });
//	 
//	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//	      em.setJpaVendorAdapter(vendorAdapter);
//	     // em.setJpaProperties(additionalProperties());
//	 
//	      return em;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//	    JpaTransactionManager transactionManager = new JpaTransactionManager();
//	    transactionManager.setEntityManagerFactory(emf);
//	 
//	    return transactionManager;
//	}


}
