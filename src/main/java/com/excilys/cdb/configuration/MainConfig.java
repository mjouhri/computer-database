package com.excilys.cdb.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@EnableWebMvc
@ComponentScan("com.excilys.cdb.controller,"
		+ "com.excilys.cdb.persistence,"
		+ "com.excilys.cdb.service" ) 
@PropertySource("classpath:datasourcemysql.properties")
public class MainConfig implements WebApplicationInitializer {
	
	@Value("${dataSource.driverClassName}")
	private String jdbcDriver;
	@Value("${dataSource.jdbcUrl}")
	private String url;
	@Value("${dataSource.user}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
		
	@Override
	public void onStartup(ServletContext ctx) throws ServletException {
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(MainConfig.class, SpringMVCConfig.class);
        webCtx.setServletContext(ctx);
        
        ServletRegistration.Dynamic servlet = ctx.addServlet("dispatcher", new DispatcherServlet(webCtx));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
	
	@Bean
	  public ViewResolver viewResolver() {
	      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	      viewResolver.setViewClass(JstlView.class);
	      viewResolver.setPrefix("/WEB-INF/views/");
	      viewResolver.setSuffix(".jsp");
	      return viewResolver;
	  }
	
	
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(emf);

	    return transactionManager;
	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//	    return new PersistenceExceptionTranslationPostProcessor();
//	}

//	 @Bean
//	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//	      LocalContainerEntityManagerFactoryBean em 
//	        = new LocalContainerEntityManagerFactoryBean();
//	      em.setDataSource(dataSource());
//	      em.setPackagesToScan(new String[] { "com.excilys.model" });
//
//	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//	      em.setJpaVendorAdapter(vendorAdapter);
//	    //  em.setJpaProperties(additionalProperties());
//
//	      return em;
//	   }

//	 Properties additionalProperties() {
//		    Properties properties = new Properties();
//		    properties.setProperty("hibernate.hbm2ddl.auto", "update");
//		    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//		    return properties;
//		}
//	
	

	
}


