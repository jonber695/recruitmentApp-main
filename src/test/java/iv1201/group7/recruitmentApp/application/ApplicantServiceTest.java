/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;
import iv1201.group7.recruitmentApp.repository.DbUtil;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import static org.assertj.core.internal.Bytes.instance;
import org.springframework.security.core.userdetails.UserDetails;


@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "iv1201.group7.recruitmentApp" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ApplicantServiceTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
public class ApplicantServiceTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private ApplicantAccountRepository repo;
    private ApplicantAccount firstApplicant;
    private ApplicantAccount secondApplicant;
    private ApplicantAccount thirdApplicant;
    @Autowired
    private ApplicantService service;
    UserDetails user;
    
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
        firstApplicant = new ApplicantAccount("TestNameOne", "TestSurname", "00000000-0000",
        "emailOne", "passwordOne");
        secondApplicant = new ApplicantAccount("TestNameTwo", "TestSurname", "00000000-0000",
        "emailTwo", "passwordTwo");
        thirdApplicant = new ApplicantAccount("TestNameThree", "NewSurname", "00000000-0000",
        "emailThree", "passwordThree");
        dbUtil.emptyDb();
    }

    @Test
    void testFindApplicant(){
        repo.save(firstApplicant);
        startNewTransaction();
        user = service.findApplicant(firstApplicant.getEmail(), firstApplicant.getPassword());
        assertThat(user.getUsername(), is(firstApplicant.getUsername()));
    }
    
    @Test 
    void testFindApplicantWrongEmail(){
        repo.save(firstApplicant);
        startNewTransaction();
        user = service.findApplicant("Wrong", firstApplicant.getPassword());
        assertThat(user == null, is(true));
    }
    
        @Test 
        void testFindApplicantWrongPassword(){
        repo.save(firstApplicant);
        startNewTransaction();
        user = service.findApplicant(firstApplicant.getEmail(), "Wrong");
        assertThat(user == null, is(true));
}
        
       @Test
       void testFindApplicantsBySurname(){
           repo.save (firstApplicant);
           startNewTransaction();
           repo.save(secondApplicant);
           startNewTransaction();
           repo.save(thirdApplicant);
           startNewTransaction();
           ArrayList<ApplicantAccount> applicantList = new ArrayList<ApplicantAccount>();
           applicantList = service.findApplicantsBySurname("TestSurname");
           assertThat(applicantList.size(), is(2));
       }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
