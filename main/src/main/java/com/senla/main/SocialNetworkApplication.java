package com.senla.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@ComponentScan({"com.senla"})
@EntityScan(basePackages = "com.senla.entity")
@EnableJpaRepositories(basePackages = "com.senla.repository")
public class SocialNetworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkApplication.class, args);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        /*Locale locale = new Locale("ru"); // - Russian localization
        slr.setDefaultLocale(locale);*/
        slr.setDefaultLocale(Locale.US);  // - English localization
        return slr;
    }
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        /*messageSource.setBasename("classpath:locale/messages_ru"); */  // - Russian localization
        messageSource.setBasename("classpath:locale/messages");       // - English version
        messageSource.setDefaultEncoding("windows-1251");
        messageSource.setCacheSeconds(3600); //refresh cache once per hour
        return messageSource;
    }
}
