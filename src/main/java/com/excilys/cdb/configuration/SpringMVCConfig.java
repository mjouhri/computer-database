package com.excilys.cdb.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Component
public class SpringMVCConfig implements WebMvcConfigurer{
	
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
	ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
	ret.setBasename("classpath:messages");
	ret.setDefaultEncoding("utf-8");
	return ret;
	}

	@Bean
	public CookieLocaleResolver localeResolver(){
	   CookieLocaleResolver resolver = new CookieLocaleResolver();
	   resolver.setDefaultLocale(Locale.ENGLISH);
	   resolver.setCookieName("myCookie");
	   resolver.setCookieMaxAge(3600);
	   return resolver;
	}

	@Bean
	   public LocaleChangeInterceptor localeChangeInterceptor() {
	       LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	       localeChangeInterceptor.setParamName("locale");
	       return localeChangeInterceptor;
	   }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(localeChangeInterceptor());
	}

}
