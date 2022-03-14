package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.Application;
import iv1201.group7.recruitmentApp.domain.ApplicationDTO;
import iv1201.group7.recruitmentApp.domain.RecruiterAccount;
import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;
import iv1201.group7.recruitmentApp.repository.ApplicationRepository;
import iv1201.group7.recruitmentApp.repository.DbUtil;
import iv1201.group7.recruitmentApp.repository.RecruiterAccountRepository;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;

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

import net.jcip.annotations.NotThreadSafe;
import static org.apache.tomcat.jni.User.username;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.springframework.security.core.userdetails.UserDetails;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"iv1201.group7.recruitmentApp"})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, RecruiterServiceTest.class,
    TransactionalTestExecutionListener.class})
@NotThreadSafe
@Transactional
@Commit
public class RecruiterServiceTest implements TestExecutionListener {

    @Autowired
    private DbUtil dbUtil;
    UserDetails user;
    @Autowired
    private RecruiterAccountRepository recruiterAccountRepo;
    @Autowired
    private RecruiterService service;
    RecruiterAccount recruiter;

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
        recruiter.setId(1);
        recruiter.setName("name");
        recruiter.setSurname("surname");
        recruiter.setUsername("username");
        recruiter.setPassword("password");
    }

    @Test
    void testFindRecruiter() {
        recruiterAccountRepo.save(recruiter);
        startNewTransaction();
        user = service.findRecruiter(recruiter.getUsername(), recruiter.getPassword());
        assertThat(user == null, is(false));
    }

    @Test
    void testFindRecruiterWrongUsername() {
        recruiterAccountRepo.save(recruiter);
        startNewTransaction();
        user = service.findRecruiter("Wrong", recruiter.getPassword());
        assertThat(user == null, is(true));
    }

    @Test
    void testFindRecruiterWrongPassword() {
        recruiterAccountRepo.save(recruiter);
        startNewTransaction();
        user = service.findRecruiter(recruiter.getUsername(), "Wrong");
        assertThat(user == null, is(true));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
