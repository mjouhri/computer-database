package com.excilys.cdb.configuration;

import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@ComponentScan("com.excilys.cdb")
@PropertySource("classpath:datasourcemysql.properties")
public class MainConfig  extends AbstractContextLoaderInitializer {
	
	@Value("${dataSource.driverClassName}")
	private String jdbcDriver;
	@Value("${dataSource.jdbcUrl}")
	private String url;
	@Value("${dataSource.user}")
	private String username;
	@Value("${dataSource.password}")
	private String password;
	
//	private static final Logger LOG = LoggerFactory.getLogger(MainConfig.class );	
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(MainConfig.class);
		
//		LOG.info("ooooooh yes");
//		
//		System.out.println("ooooooh yes");
		
		return rootContext;
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
	
}
