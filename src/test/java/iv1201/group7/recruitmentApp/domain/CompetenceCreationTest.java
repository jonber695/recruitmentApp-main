package iv1201.group7.recruitmentApp.domain;

/**
 *
 * @author sarab
 */

import iv1201.group7.recruitmentApp.application.CompetenceProfileService;
import iv1201.group7.recruitmentApp.repository.CompetenceProfileRepository;
import iv1201.group7.recruitmentApp.repository.DbUtil;
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
import java.util.ArrayList;
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

import net.jcip.annotations.NotThreadSafe;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"iv1201.group7.recruitmentApp"})

@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, 
    CompetenceCreationTest.class,
    TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class CompetenceCreationTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    //@Autowired
    //private some repository - account repository?
    private Application application;
    @Autowired
    private CompetenceProfileRepository competenceProfileRepo;
    List<Double> experience;
    ApplicantAccount applicant;
    List<Competence> competences;
    @Autowired
    CompetenceProfileService service;
    List<CompetenceProfile> competenceProfile;
    Competence competence;
    CompetenceCreation comp;
    
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
        competence = new Competence();
        competence.setDefId(1);
        competence.setName("Sales");
        competence.setLanguage("en");
        comp = new CompetenceCreation(competences);
    }
    
  /*  List<Competence>
List<Double>

void addCompetence(Competence)
List<Competence> getCompetences()
void addYearsOfExperience(double year)
List<Double> getYearsOfExperience()  */
    @Test
    void testAddCompetence() {
        comp.addCompetence(competence);
        assertThat(comp.competences.get(0).equals(null), is(not(true)));
    }
    
   @Test
    void testNotEqualToNull() {
        assertThat(comp.equals(null), is(not(true)));
    }
    
        @Test
    void testNotEqualToOtherClassObject() {
        assertThat(comp.equals(new Object()), is(not(true)));
    }

}
