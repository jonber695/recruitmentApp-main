/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import iv1201.group7.recruitmentApp.repository.CompetenceProfileRepository;
import iv1201.group7.recruitmentApp.repository.DbUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import net.jcip.annotations.NotThreadSafe;
import static org.hamcrest.MatcherAssert.assertThat;
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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, CompetenceProfileTest.class,
        TransactionalTestExecutionListener.class })
@NotThreadSafe
@Transactional
@Commit
class CompetenceProfileTest implements TestExecutionListener{
    @Autowired
    private DbUtil dbUtil;
    @Autowired
    private CompetenceProfileRepository repo;
    private CompetenceProfile competenceProfile;
    
    private final int competenceId = 1;
    private final int applicantId = 1;
    private final BigDecimal yearsOfExperience = new BigDecimal("1.0");
    
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
        competenceProfile = new CompetenceProfile(competenceId, applicantId, yearsOfExperience);
        dbUtil.emptyDb();
    }
    
    @Test
    void testNotEqualToOtherClassObject(){
        assertThat(competenceProfile.equals(new Object()), is(not(true)));
    }
    
    @Test
    void testNotNull() {
        assertThat(competenceProfile.equals(null), is(not(true)));
    }
    
    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
