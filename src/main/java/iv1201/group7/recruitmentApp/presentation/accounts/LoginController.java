package iv1201.group7.recruitmentApp.presentation.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import iv1201.group7.recruitmentApp.application.RecruiterService;
import iv1201.group7.recruitmentApp.application.ApplicantService;
import iv1201.group7.recruitmentApp.application.SecurityUserDetailsService;
import iv1201.group7.recruitmentApp.domain.RecruiterAccountDTO;
import iv1201.group7.recruitmentApp.domain.ApplicantAccountDTO;
import javax.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;


/**
 * @author Group 7
 */
@Controller
@Scope("session")
/**
 * The class responsible for returning the correct view.
 */
public class LoginController {
    @Autowired
    private RecruiterService recruiterService;
    private RecruiterAccountDTO recruiterAcct;
    @Autowired
    private ApplicantService applicantService;
    private ApplicantAccountDTO applicantAcct;
    @Autowired
    private SecurityUserDetailsService securityService;
    private UserDetails recruiter;
    private UserDetails applicant;
    
    /**
     * Redirects to the login page when accessed from "localhost:8080/".
     * @return The login.html page
     */
    @GetMapping("/")
    public String showDefaultView() {
        return "redirect:login";
    }
    
    /*
    * Handles HTTP GET requests for /login
    * @return The login.html page
    */
    @GetMapping("/login")
    public String showLoginView(LoginForm loginForm) {
        return "login";
    }
    
    /*
    * Adds the currentAcct attribute to the model before redirecting to searchAppls.
    * @return The searchAppls.html page 
    */
    private String showRecruiterAcctPage(Model model, SearchForm searchForm) {
        if (recruiterAcct != null) {
            model.addAttribute("currentAcct", recruiterAcct);
        }
        if (searchForm != null) {
            model.addAttribute("searchForm", searchForm);
        }
        return "redirect:search";
    }
    
    /*
    * Adds the currentAcct attribute to the model before redirecting to searchAppls.
    * @return The searchAppls.html page 
    */
    private String showApplicantAcctPage(Model model, CreateForm createForm) {
        if (applicantAcct != null) {
            model.addAttribute("currentAcct", applicantAcct);
        }
        if (createForm != null) {
            model.addAttribute("createForm", createForm);
        }
        return "redirect:create";
    }
    
    /**
     * Handles HTTP POST requests for /login
     * @param loginForm The form object containing user information 
     *                  entered into the form.
     * @param bindingResult The validation results.
     * @param model The object model containing various attributes for 
     *              use in Thymeleaf.
     * @return The showRecrAcctPage.html page if currentAcct is not null,
     *          otherwise returns the login.html page
     */
    @PostMapping("/login")
    public String findAccount(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
        System.out.println("we made it 1");
        if(bindingResult.hasErrors()){
            //model.addAttribute("loginForm", new LoginForm());
            return "login";
        }
        
        if (!loginForm.getLoginAsRecruiter()){
            applicant = applicantService.findApplicant(loginForm.getUsername(), loginForm.getPassword());
            if (applicant == null) { return "login"; }
            
            return showApplicantAcctPage(model, new CreateForm());
        } else if(loginForm.getLoginAsRecruiter()){
            System.out.println("we made it 2");
            recruiter = securityService.loadUserByUsername(loginForm.getUsername());
            //recruiterAcct = recruiterService.findRecruiter(loginForm.getUsername(), loginForm.getPassword());
            if (recruiterAcct == null) { return "login"; }
            
            return showRecruiterAcctPage(model, new SearchForm());
        } 
        
        return "login";
    }
    
}
