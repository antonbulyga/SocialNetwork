package com.senla.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalizationConfiguration {

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
