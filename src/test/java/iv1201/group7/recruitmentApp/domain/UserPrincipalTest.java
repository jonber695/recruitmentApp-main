/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import iv1201.group7.recruitmentApp.repository.CompetenceRepository;
import iv1201.group7.recruitmentApp.repository.DbUtil;
import java.io.IOException;
import java.sql.SQLException;
import net.jcip.annotations.NotThreadSafe;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jacob Dwyer
 */
@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "iv1201.group7.recruitmentApp" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, UserPrincipalTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class UserPrincipalTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    private ApplicantAccount applicant;
    private RecruiterAccount recruiter;
    private UserPrincipal userPrincipal;
    
    @Override
    public void beforeTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        dbUtil = testContext.getApplicationContext().getBean(DbUtil.class);
        enableCreatingEMFWhichIsNeededForTheApplicationContext();
    }

    @Override
    public void afterTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        enableCreatingEMFWhichIsNeededForTheApplicationContext();
    }

    private void enableCreatingEMFWhichIsNeededForTheApplicationContext()
            throws SQLException, IOException, ClassNotFoundException {
        dbUtil.emptyDb();
    }
    
    @BeforeEach
    void setup() throws SQLException, IOException, ClassNotFoundException {
        userPrincipal = new UserPrincipal(applicant);
        dbUtil.emptyDb();
    }
    
    @Test
    void testApplicantAndIsRecruiterFalse() {
        applicant = new ApplicantAccount();
        userPrincipal = new UserPrincipal(applicant);
        assertThat(ApplicantAccount.class.isInstance(userPrincipal.getApplicantObject()) 
                && !userPrincipal.getRecruiterStatus(), is(true));
    }
    
    @Test
    void testRecruiterAndIsRecruiterTrue() {
        recruiter = new RecruiterAccount();
        userPrincipal = new UserPrincipal(recruiter);
        assertThat(RecruiterAccount.class.isInstance(userPrincipal.getRecruiterObject()) 
                && userPrincipal.getRecruiterStatus(), is(true));
    }
    
    @Test
    void testNotEqualToOtherClassObject(){
        assertThat(userPrincipal.equals(new Object()), is(not(true)));
    }
    
    @Test
    void testNotNull() {
        assertThat(userPrincipal.equals(null), is(not(true)));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
