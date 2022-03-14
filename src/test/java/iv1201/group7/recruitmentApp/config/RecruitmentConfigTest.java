package iv1201.group7.recruitmentApp.config;

// import static org.hamcrest.MatcherAssert.assertThat;
import iv1201.group7.recruitmentApp.repository.DbUtil;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"iv1201.group7.recruitmentApp"})
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, RecruitmentConfigTest.class })
class RecruitmentConfigTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private WebApplicationContext webappContext;
    private MockMvc mockMvc;

    @Override
    public void beforeTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        dbUtil = testContext.getApplicationContext().getBean(DbUtil.class);
        enableCreatingEMFWhichIsNeededForTheApplicationContext();
    }

    private void enableCreatingEMFWhichIsNeededForTheApplicationContext()
            throws SQLException, IOException, ClassNotFoundException {
        dbUtil.emptyDb();
    }

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webappContext).build();
    }

   /* @Test
    void testCorrectServerPropsCreated(@Autowired RecruitmentConfig conf) {
        assertEquals("/recruitmentApp", conf.serverProperties().getContextRoot(), "Wrong ServerProperties instance.");
    }*/

    @Test
    void testCorrectTemplEngineIsUsed(@Autowired @Qualifier("recruitmentAppTemplateEngine") SpringTemplateEngine templateEngine,
            @Autowired ThymeleafViewResolver viewResolver) {
        assertEquals(templateEngine, viewResolver.getTemplateEngine(), "Wrong template engine.");
    }

    @Test
    void testCorrectTemplResolverIsUsed(@Autowired ThymeleafViewResolver viewResolver,
            @Autowired SpringResourceTemplateResolver templateResolver) {
        assertTrue(((SpringTemplateEngine) viewResolver.getTemplateEngine()).getTemplateResolvers()
                .contains(templateResolver), "Wrong template resolver.");
    }

    /*@Test
    void testCorrectI18nBeanCreated(@Autowired LocaleChangeInterceptor i18nBean) {
        assertThat("Wrong properties in i18n bean.", i18nBean,
                allOf(hasProperty("paramName", equalTo("lang")),
                        hasProperty("httpMethods", arrayContainingInAnyOrder("GET", "POST")),
                        hasProperty("ignoreInvalidLocale", equalTo(true))));
    }

    @Test
    void testCorrectMsgSourceBeanCreated(@Autowired ReloadableResourceBundleMessageSource messageSource) {
        assertThat("Wrong properties in message source bean.", messageSource, hasProperty("basenameSet",
                containsInAnyOrder("classpath:/i18n/Messages", "classpath:/i18n/ValidationMessages")));
    }*/
}
