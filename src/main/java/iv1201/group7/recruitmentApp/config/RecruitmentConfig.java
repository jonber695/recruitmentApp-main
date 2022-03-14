package iv1201.group7.recruitmentApp.config;

import java.util.Locale;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author Group 7
 */
/**
 * Configuration for the recruitment app. 
 * 
 * Additional config settings can be found in the file 
 * <code>application.properties</code>.
 */
@EnableTransactionManagement
@EnableWebMvc
@Configuration
public class RecruitmentConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    /**
     * Sets the application context.
     * 
     * @param applicationContext The application context.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Creates ServerProperties using properties from the application.properties file.
     */
    @Bean
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }
    
    /**
     * Creates a ThymeleafViewResolver bean in order to enable Thymeleaf templates.
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("text/html; charset=UTF-8");
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    /**
     * Creates a SpringTemplateEngine that integrates Thymeleaf and Spring.
     */
    @Bean(name = "recruitmentAppTemplateEngine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

    /**
     * Creates a SpringResourceTemplateResolver that integrates Thymeleaf and Spring.
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    /**
     * Used for the i18n localization functionality.
     * @param registry Takes an InterceptorRegistry as a parameter.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Creates a LocaleChangeInterceptor for localization functionality.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        String nameOfHttpParamForLangCode = "lang";
        String[] allowedHttpMethodsForLocaleChange = { "GET", "POST" };

        LocaleChangeInterceptor i18nBean = new LocaleChangeInterceptor();
        i18nBean.setParamName(nameOfHttpParamForLangCode);
        i18nBean.setHttpMethods(allowedHttpMethodsForLocaleChange);
        i18nBean.setIgnoreInvalidLocale(true);
        return i18nBean;
    }
    
    /**
     * Creates a SessionLocaleResolver for storing the user's current locale
     * @return a SessionLocaleResolver with English as its default locale
     */
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));
        return localeResolver;
    }

    /**
     * Creates a ReloadableResourceBundleMessageSource that loads resources for
     * localization.
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        String l10nMsgDir = "classpath:/i18n/Messages";
        String l10nValidationMsgDir = "classpath:/i18n/ValidationMessages";
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.addBasenames(l10nMsgDir, l10nValidationMsgDir);
        resource.setDefaultEncoding("UTF-8");
        resource.setFallbackToSystemLocale(false);
        return resource;
    }

    /**
     * Creates a Validator for validating messages.
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }
}
