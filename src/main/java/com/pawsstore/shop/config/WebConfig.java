package com.pawsstore.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * LocaleResolver определяет, как приложение будет запоминать выбранный язык.
     * CookieLocaleResolver сохраняет выбор в куки браузера.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver clr = new CookieLocaleResolver("client-language");
        clr.setDefaultLocale(Locale.ENGLISH); // Язык по умолчанию — английский
        clr.setCookieMaxAge(Duration.ofDays(7)); // Куки хранятся 7 дней
        return clr;
    }

    /**
     * LocaleChangeInterceptor отслеживает параметр в URL (например, ?lang=ru).
     * Если параметр найден, он меняет текущую локаль пользователя.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Имя параметра в запросе
        return lci;
    }

    /**
     * Регистрация перехватчика в системе Spring MVC.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}