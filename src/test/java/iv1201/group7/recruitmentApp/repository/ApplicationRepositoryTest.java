package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.Application;
import java.io.IOException;
import java.sql.SQLException;
import net.jcip.annotations.NotThreadSafe;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, 
    ApplicantAccountRepositoryTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class ApplicationRepositoryTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private ApplicationRepository applicationRepo;
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
        application = new Application();
        dbUtil.emptyDb();
    }
    
    @Test
    void findApplicationById(){
        applicationRepo.save(application);
        startNewTransaction();
        Application foundApplication = applicationRepo.findApplicationById(application.getId());
        assertThat("failure - ID of found application not same as that of saved application", 
                foundApplication.getId(), is(application.getId()));
    }
    
    @Test
    void testFindApplicationByApplicantId(){
        application.setApplicantId(1);
        applicationRepo.save(application);
        startNewTransaction();
        Application foundApplication = applicationRepo.findApplicationByApplicantId(application.getApplicantId());
        assertThat("failure - applicant ID of found application not same as that of saved application", 
                foundApplication.getApplicantId(), is(application.getApplicantId()));
    }
    
    @Test
    void testFindNonexistingApplicationById(){
        applicationRepo.save(application);
        startNewTransaction();
        Application foundApplication = applicationRepo.findApplicationById(-1);
        assertThat(foundApplication == null, is(true));
    }
    
    @Test
    void testFindNonexistingApplicationByApplicantId(){
        applicationRepo.save(application);
        startNewTransaction();
        Application foundApplication = applicationRepo.findApplicationByApplicantId(-1);
        assertThat(foundApplication == null, is(true));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
