package com.senla.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocalizationConfiguration {

    @Value("${language}")
    private String language;

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        switch (language) {
            case "Russian":
                Locale locale = new Locale("ru");
                slr.setDefaultLocale(locale);
                break;
            default:
                slr.setDefaultLocale(Locale.US);
                break;
        }
        return slr;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        switch (language) {
            case "Russian":
                messageSource.setBasename("classpath:locale/messages_ru");
                break;
            default:
                messageSource.setBasename("classpath:locale/messages");
                break;
        }
        messageSource.setDefaultEncoding("windows-1251");
        messageSource.setCacheSeconds(3600); //refresh cache once per hour
        return messageSource;
    }
}
