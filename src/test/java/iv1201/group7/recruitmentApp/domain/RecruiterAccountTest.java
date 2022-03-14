/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import iv1201.group7.recruitmentApp.repository.DbUtil;
import iv1201.group7.recruitmentApp.repository.RecruiterAccountRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import net.jcip.annotations.NotThreadSafe;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, RecruiterAccountTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class RecruiterAccountTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private RecruiterAccountRepository recruiterRepo;
    private RecruiterAccount recruiter;
    
    private final String username = "someusername";
    private final String name = "john";
    private final String surname = "doe";
    private final String password = "somepw";
    
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
        recruiter.setUsername(username);
        recruiter.setName(name);
        recruiter.setSurname(surname);
        recruiter.setPassword(password);
        dbUtil.emptyDb();
    }
    
    @Test
    void testUsernameIsSame(){
        recruiterRepo.save(recruiter);
        startNewTransaction();
        RecruiterAccount foundRecruiter = recruiterRepo.findRecruiterByUsername(username);
        assertThat(foundRecruiter.getUsername().equals(recruiter.getUsername()), is(true));
    }
    
    @Test
    void testNameIsSame(){
        recruiterRepo.save(recruiter);
        startNewTransaction();
        RecruiterAccount foundRecruiter = recruiterRepo.findRecruiterByUsername(username);
        assertThat(foundRecruiter.getName().equals(recruiter.getName()), is(true));
    }
    
    @Test
    void testIdIsSame(){
        recruiterRepo.save(recruiter);
        startNewTransaction();
        RecruiterAccount foundRecruiter = recruiterRepo.findRecruiterByUsername(username);
        assertThat(foundRecruiter.getId() == recruiter.getId(), is(true));
    }
    
    @Test
    void testAuthoritiesNotNull() {
        recruiterRepo.save(recruiter);
        startNewTransaction();
        Set<GrantedAuthority> foundRecruiterAuths = recruiterRepo.findRecruiterByUsername(username)
                .getAuthorities();
        assertThat(foundRecruiterAuths == null, is(false));
    }
    
    @Test
    void testNotEqualToOtherClassObject(){
        assertThat(recruiter.equals(new Object()), is(not(true)));
    }
    
    @Test
    void testNotNull() {
        assertThat(recruiter.equals(null), is(not(true)));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
