package iv1201.group7.recruitmentApp.presentation.accounts;

import iv1201.group7.recruitmentApp.repository.DbUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"iv1201.group7.recruitmentApp"})
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, SearchFormTest.class })
class SearchFormTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private Validator validator;

    @Override
    public void beforeTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        dbUtil = testContext.getApplicationContext().getBean(DbUtil.class);
        enableCreatingEMFWhichIsNeededForTheApplicationContext();
    }

    private void enableCreatingEMFWhichIsNeededForTheApplicationContext()
            throws SQLException, IOException, ClassNotFoundException {
        dbUtil.emptyDb();
    }

           @Test
            void testExistingSurname(){
                SearchForm search = new SearchForm();
                search.setSurname("Surname");
                Set<ConstraintViolation<SearchForm>> result = validator.validate(search);
                assertThat(result, is(empty()));
            }
            
            @Test
            void testNonExistingSurname() {
            String expectedMsg = "{search.surname.missing}";
            SearchForm search = new SearchForm();
            search.setSurname("");
            Set<ConstraintViolation<SearchForm>> result = validator.validate(search);
            assertThat(result.size(), is(1));
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedMsg))));
    }
}