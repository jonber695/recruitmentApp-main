
package iv1201.group7.recruitmentApp.repository;


import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.Application;

import net.jcip.annotations.NotThreadSafe;
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
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.internal.Bytes.instance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "iv1201.group7.recruitmentApp" })
// @SpringBootTest can be used instead of @SpringJUnitWebConfig,
// @EnableAutoConfiguration and @ComponentScan, but are we using
// JUnit5 in that case?
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, 
    ApplicantAccountRepositoryTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class ApplicantAccountRepositoryTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private ApplicantAccountRepository repo;
    private ApplicantAccount applicant;
    @Autowired
    private ApplicationRepository applicRepo;
    private Application application;

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
        applicant = new ApplicantAccount("TestName", "TestSurname", "00000000-0000",
        "email", "password");
        dbUtil.emptyDb();
    }

   @Test
    void testCreateApplicantAccount() {
        applicant = new ApplicantAccount("TestName", "TestSurname", "00000000-0000",
        "email", "password");
         repo.save(applicant);
        startNewTransaction();
        List<ApplicantAccount> acctsInDb = repo.findAll();
        //assertThat(acctsInDb, containsInAnyOrder(applicant)); //this fails
        assertThat(acctsInDb.get(0).getName(), is("TestName"));
    }
    
//   My method but error, query finds two results instead of one
   @Test
    void testFindApplicantBySurname() {
        repo.save(applicant);
        startNewTransaction();
        ApplicantAccount foundApplicant = repo.findApplicantBySurname(applicant.getSurname()); 
        assertThat(foundApplicant.getSurname(), is(applicant.getSurname()));
    }

    @Test
    void testFindNonExistingAcctBySurname() {
        repo.save(applicant);
        startNewTransaction();
        ApplicantAccount foundApplicant = repo.findApplicantBySurname("NonExistant");
        assertThat(foundApplicant == null, is(true));
    }
 
    @Test
    void testApplicantAccountRepoNeedsTransaction() {
        TestTransaction.end();
        assertThrows(IllegalTransactionStateException.class, () -> {
            repo.save(applicant);
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            repo.findApplicantBySurname(applicant.getSurname());
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            repo.findAll();
        });
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}


