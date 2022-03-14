/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import iv1201.group7.recruitmentApp.repository.CompetenceRepository;
import iv1201.group7.recruitmentApp.repository.DbUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, CompetenceTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class CompetenceTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private CompetenceRepository repo;
    private Competence competence;
    
    private final int defId = 1;
    private final String lang = "en";
    private final String name = "ticket sales";
    
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
        competence = new Competence();
        competence.setDefId(defId);
        competence.setLanguage(lang);
        competence.setName(name);
        dbUtil.emptyDb();
    }
    
    @Test
    void testCompetenceGeneratedIdStartsAtZero(){
        assertThat(competence.getId(), is(0));
    }
    
    /*@Test // NOT DONE
    void testLanguageIsNotNull(){
        repo.save(competence);
        startNewTransaction();
        List<Competence> foundComps = repo.findAll();
        assertThat(foundComps, containsInAnyOrder(competence));
    }*/
    
    @Test
    void testDefinitionIdIsCorrect(){
        repo.save(competence);
        startNewTransaction();
        Competence foundComp = repo.getCompetenceById(defId);
        assertThat(foundComp.getDefId(), is(competence.getDefId()));
    }
    
    @Test
    void testNotEqualToOtherClassObject(){
        assertThat(competence.equals(new Object()), is(not(true)));
    }
    
    @Test
    void testNotNull() {
        assertThat(competence.equals(null), is(not(true)));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
