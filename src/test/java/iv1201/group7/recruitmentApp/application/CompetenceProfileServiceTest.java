package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.Application;
import iv1201.group7.recruitmentApp.domain.ApplicationDTO;
import iv1201.group7.recruitmentApp.domain.Competence;
import iv1201.group7.recruitmentApp.domain.CompetenceProfile;
import iv1201.group7.recruitmentApp.domain.RecruiterAccount;
import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;
import iv1201.group7.recruitmentApp.repository.ApplicationRepository;
import iv1201.group7.recruitmentApp.repository.CompetenceProfileRepository;
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
import static org.apache.tomcat.jni.User.username;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.springframework.security.core.userdetails.UserDetails;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"iv1201.group7.recruitmentApp"})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, CompetenceProfileServiceTest.class,
    TransactionalTestExecutionListener.class})
@NotThreadSafe
@Transactional
@Commit
public class CompetenceProfileServiceTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private CompetenceProfileRepository competenceProfileRepo;
    List<Double> experience;
    ApplicantAccount applicant;
    List<Competence> competences;
    @Autowired
    CompetenceProfileService service;
    List<CompetenceProfile> competenceProfile;
    
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
         dbUtil.emptyDb();
        applicant = new ApplicantAccount("TestNameOne", "TestSurname", "00000000-0000",
        "emailOne", "passwordOne");
        applicant.setId(1);
        experience = new ArrayList<Double>();
        experience.add(0.0);
        experience.add(1.0);
        experience.add(2.0);
        Competence competence1 = new Competence();
        competence1.setDefId(1);
        competence1.setName("Coaster");
        competence1.setLanguage("en");
        Competence competence2 = new Competence();
        competence2.setDefId(1);
        competence2.setName("Cleaning");
        competence2.setLanguage("en");
        Competence competence3 = new Competence();
        competence3.setDefId(1);
        competence3.setName("Sales");
        competence3.setLanguage("en");
        competences = new ArrayList<Competence>();
        competences.add(competence1);
        competences.add(competence2);
        competences.add(competence3);
        
    }

    @Test
    void testCreateCompetenceProfile() {
        competenceProfile = new ArrayList<CompetenceProfile>();
        competenceProfile = service.createCompetenceProfile(experience, applicant, competences);
        assertThat(competenceProfile.get(0).getApplicantId() == 1, is(true));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
