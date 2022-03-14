package iv1201.group7.recruitmentApp.domain;

/**
 *
 * @author sarab
 */

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
//import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import iv1201.group7.recruitmentApp.repository.DbUtil;
import net.jcip.annotations.NotThreadSafe;
//import se.kth.iv1201.appserv.bank.repository.AccountRepository; - import repo
//import se.kth.iv1201.appserv.bank.repository.DbUtil; - import util

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"iv1201.group7.recruitmentApp"})
// @SpringBootTest can be used instead of @SpringJUnitWebConfig,
// @EnableAutoConfiguration and @ComponentScan, but are we using
// JUnit5 in that case?

// Med @SpringBootTest sker anropet över HTTP, dvs "på riktigt" och inte med mockad server.
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, 
    ApplicantAccountTest.class,
    TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class ApplicantAccountTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    //@Autowired
    //private some repository - account repository?
    private ApplicantAccount account;

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
    void setup() throws SQLException, IOException, ClassNotFoundException {
        account = new ApplicantAccount("TestName", "TestSurname", "00000000-0000",
        "email", "password");
    }
    
    @Test
    void testAcctNoIsGenerated() {
        assertThat(account.getName(), is(not("somethingElse")));
    }
    
    @Test
    void testNotEqualToNull() {
        assertThat(account.equals(null), is(not(true)));
    }
    
        @Test
    void testNotEqualToOtherClassObject() {
        assertThat(account.equals(new Object()), is(not(true)));
    }

    @Test
    void testEqualToIdenticalAccount() {
        assertThat(account.equals(account), is(true));
    }
}
