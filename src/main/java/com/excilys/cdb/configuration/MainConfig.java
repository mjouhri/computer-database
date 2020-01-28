package com.excilys.cdb.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@EnableWebSecurity
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
	
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
	
}


