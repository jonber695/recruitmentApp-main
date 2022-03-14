package iv1201.group7.recruitmentApp.repository;

import iv1201.group7.recruitmentApp.domain.Competence;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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
    CompetenceRepositoryTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
public class CompetenceRepositoryTest implements TestExecutionListener {
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private CompetenceRepository compRepo;
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
    }
    
    @Test
    void testGetCompetenceById(){
        compRepo.save(competence);
        startNewTransaction();
        Competence foundCompetence = compRepo.getCompetenceById(competence.getId()); 
        assertThat(foundCompetence.getId(), is(competence.getId()));
    }
    
    @Test
    void testGetNonexistingCompetenceById(){
        Competence foundCompetence = compRepo.getCompetenceById(-1); 
        assertThat(foundCompetence == null, is(true));
    }
    
    @Test
    void testFindMultipleCompetenceByLanguage(){
        List<Competence> competenceList = compRepo.findMultipleCompetenceByLanguage(lang);
        ArrayList<String> foundLangs = new ArrayList<>();
        ArrayList<String> expectedLangs = new ArrayList<>();
        for(Competence comps : competenceList){
            foundLangs.add(comps.getLanguage());
            expectedLangs.add(lang);
        }
        
        assertIterableEquals(foundLangs, expectedLangs);
    }
    
    @Test
    void testGetCompetenceByName(){
        competence = new Competence();
        competence.setDefId(2);
        competence.setLanguage(lang);
        competence.setName("lotteries");
        compRepo.save(competence);
        startNewTransaction();
        Competence foundCompetence = compRepo.getCompetenceByName(competence.getName()); 
        assertThat(foundCompetence.getName(), is(competence.getName()));
    }
    
    @Test
    void testGetNonexistingCompetenceByName(){
        Competence foundCompetence = compRepo.getCompetenceByName("nonexisting"); 
        assertThat(foundCompetence == null, is(true));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
