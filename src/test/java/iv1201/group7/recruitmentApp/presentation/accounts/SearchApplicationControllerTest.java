package iv1201.group7.recruitmentApp.presentation.accounts;

import static iv1201.group7.recruitmentApp.presentation.accounts.PresentationTestHelper.sendGetRequest;
import static iv1201.group7.recruitmentApp.presentation.accounts.PresentationTestHelper.sendPostRequest;
import static iv1201.group7.recruitmentApp.presentation.accounts.PresentationTestHelper.containsElements;
import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import static iv1201.group7.recruitmentApp.presentation.accounts.PresentationTestHelper.addParam;



import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;


import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
/**
 * @author Group 7
 */

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "iv1201.group7.recruitmentApp" })

@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, 
        SearchApplicationControllerTest.class,
        TransactionalTestExecutionListener.class })
@Transactional
@Commit
public class SearchApplicationControllerTest implements TestExecutionListener{
    @Autowired
    ApplicantAccountRepository applicantAccountRepo;
    @Autowired 
    private WebApplicationContext webappContext;
    private MockMvc mockMvc;
    private ApplicantAccount applicantAccount;
  
    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webappContext).build();
        applicantAccount = new ApplicantAccount("Leroy", "Crane", 
                null, null, null);
    }
    
    //Tests that GET-request to "search" returns "search".
    @Test
    void testCorrectViewForSearchURL() throws Exception {
        sendGetRequest(mockMvc, "/search").andExpect(status().isOk()).
                andExpect(isSearchPage());
    }
    
    //Same as above but for searchresults
    @Test
    void testCorrectViewForSearchresultsURL() throws Exception {
        sendGetRequest(mockMvc, "/searchresults").andExpect(status().isOk()).
        andExpect(isSearchresultsPage());
    }
    
    //Tests that the POST-request for viewapplication works
    /*@Test
    void testViewapplication() throws Exception {
        sendPostRequest(mockMvc, "/viewapplication",addParam("applicantId", "11")).
                andExpect(status().isOk()).
                andExpect(isViewapplicationPage());
    }*/
    
    //Next two tests check that accepted and rejected pages are working
    @Test
    void testCorrectViewForAcceptedURL() throws Exception {
        sendGetRequest(mockMvc, "/accepted").andExpect(status().isOk()).
                andExpect(isAcceptedPage());
    }
    
    @Test
    void testCorrectViewForRejectedURL() throws Exception {
        sendGetRequest(mockMvc, "/rejected").andExpect(status().isOk()).
                andExpect(isRejectedPage());
    }

    
    //Tests that validation works when entering empty string in search box
    @Test
    void testSearchEmptySurname() throws Exception {
        sendPostRequest(mockMvc, "/search", addParam("surname", ""))
                .andExpect(status().isOk()).andExpect(isSearchPage())
                .andExpect(containsErrorMsg("search-surname", "enter a surname"));
    }
    
        //Test that searching for an existing user redirects to the next page
        
        //Test that searching for a non-existing user doesn't get to next page
    
    private ResultMatcher isSearchPage() {
        return containsElements("h1:contains(Search Applicants)");
    }
    
    private ResultMatcher isSearchresultsPage() {
        return containsElements("h1:contains(Search Results)");
    }
    
    private ResultMatcher isViewapplicationPage(){
        return containsElements("h1:contains(Viewing Application)");
    }
    
    private ResultMatcher isAcceptedPage(){
        return containsElements("h1:contains(Application accepted)");
    }
    
        private ResultMatcher isRejectedPage(){
        return containsElements("h1:contains(Application rejected)");
    }
    
    private ResultMatcher containsErrorMsg(String idForErrorElem, String errorMsg) {
        return containsElements("input[id=" + idForErrorElem + "]" + "~span.error:contains(" + errorMsg + ")"); 
    }
}
