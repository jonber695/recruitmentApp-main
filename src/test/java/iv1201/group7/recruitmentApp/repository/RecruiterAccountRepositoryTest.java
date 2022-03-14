package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.RecruiterAccount;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
    RecruiterAccountRepositoryTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
public class RecruiterAccountRepositoryTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private RecruiterAccountRepository repo;
    private RecruiterAccount recruiter;
    
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
        recruiter = new RecruiterAccount();
        dbUtil.emptyDb();
    }
    
    @Test
    void testFindRecruiterByUsername(){
        repo.save(recruiter);
        startNewTransaction();
        RecruiterAccount foundRecruiter = repo.findRecruiterByUsername(recruiter.getUsername()); 
        assertThat(foundRecruiter.getUsername(), is(recruiter.getUsername()));
    }
    
    @Test
    void testFindRecruiterByNonexistingUsername(){
        RecruiterAccount foundRecruiter = repo.findRecruiterByUsername("nonexisting"); 
        assertThat(foundRecruiter == null, is(true));
    }
    
    @Test
    void testFindAll(){
        repo.save(recruiter);
        startNewTransaction();
        repo.save(new RecruiterAccount());
        startNewTransaction();
        List<RecruiterAccount> foundAccts = repo.findAll();
        assertThat(foundAccts.size() == 2, is(true));
    }
    
    @Test
    void testFindAllNoAccounts(){
        List<RecruiterAccount> foundAccts = repo.findAll();
        assertThat(foundAccts.size() == 0, is(true));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
    
}
