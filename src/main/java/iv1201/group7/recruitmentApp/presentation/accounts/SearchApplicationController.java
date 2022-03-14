
package iv1201.group7.recruitmentApp.presentation.accounts;

import iv1201.group7.recruitmentApp.application.ApplicantService;
import iv1201.group7.recruitmentApp.application.ApplicationService;
import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.ApplicantAccountDTO;
import iv1201.group7.recruitmentApp.domain.Application;
import iv1201.group7.recruitmentApp.domain.ApplicationDTO;
import iv1201.group7.recruitmentApp.domain.HandleApplicationException;
import iv1201.group7.recruitmentApp.domain.UserPrincipal;
import java.security.Principal;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Group 7
 */

@Controller
@Scope("session")
/**
 * The class responsible for returning the correct view related to application
 * searches.
 */
public class SearchApplicationController {
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private ApplicationService applicationService;
    private ApplicantAccountDTO viewedApplicant;
    private ArrayList<ApplicantAccount> applicantList;
    private ApplicationDTO application;
    private int version;
    
    /**
     * Handles HTTP GET requests for /search
     * @param searchForm The submitted form
     * @return The search.html page.
     */
    @GetMapping("/search")
    public String showSearchApplicationView(SearchForm searchForm) {
        return "search";
    }
    
    @GetMapping("/searchresults")
    public String showNewPage(ViewApplicationForm viewApplicationForm) {
        return "searchresults";
    }
    
    @GetMapping("/accepted")
    public String showAccepted() {
            return "accepted";
    }
    
    @GetMapping("/rejected")
    public String showRejected() {
        return "rejected";
    }
    
    @PostMapping("/search")
    public String findApplicant(@Valid SearchForm searchForm, BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            return "search";
        }
        
        applicantList = applicantService.findApplicantsBySurname(searchForm.getSurname());
        if (applicantList.isEmpty()) {
            return "search";
        }
        return showApplicant(model);
    }
    
        private String showApplicant(Model model) {
        if (!applicantList.isEmpty()) {
            model.addAttribute("viewedApplicant", applicantList); //May need to rename
        }
        return "searchresults";
    }
        
        
        @PostMapping("/searchresults")
        public String viewResults(ViewApplicationForm viewApplicationForm, Model model) 
        throws HandleApplicationException{
            application = applicationService.findApplicationByApplicantId(viewApplicationForm.getApplicantId(), "search");
            model.addAttribute("viewedApplication", application);
            return "viewapplication";
        }
        
        @PostMapping("viewapplication")
        public String handleApplication(HandleApplicationForm handleApplicationForm, Model model, Authentication auth) 
        throws HandleApplicationException {
            UserPrincipal recruiter = (UserPrincipal) auth.getPrincipal();
            Application handledApplication = new Application();
            version = application.getVersion();
            if (handleApplicationForm.getNewStatus().equals("accepted")){
                applicationService.updateApplication(handledApplication,true, recruiter.getRecruiterObject().getId(), application,version);
                return "accepted";
            } if (handleApplicationForm.getNewStatus().equals("rejected")) {
                applicationService.updateApplication(handledApplication,false,recruiter.getRecruiterObject().getId(), application,version);
                return "rejected";
            }
            return "newhtml";
        }
 
}
