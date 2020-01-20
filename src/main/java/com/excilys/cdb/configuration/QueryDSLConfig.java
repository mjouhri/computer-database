//package com.excilys.cdb.configuration;
//
//import java.util.Properties;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Component
//@EnableTransactionManagement
//public class QueryDSLConfig {
//	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//	      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//	      em.setDataSource(dataSource);
//	      em.setPackagesToScan(new String[] { "fr.excilys.cdb.persistence.models" });
//	 
//	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//	      em.setJpaVendorAdapter(vendorAdapter);
//	      em.setJpaProperties(additionalProperties());
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
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//	    return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	Properties additionalProperties() {
//	    Properties properties = new Properties();
//	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");    
//	    return properties;
//	}
//
//}
