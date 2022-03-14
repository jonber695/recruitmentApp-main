package iv1201.group7.recruitmentApp.presentation.accounts;

import iv1201.group7.recruitmentApp.application.ApplicationService;
import iv1201.group7.recruitmentApp.application.CompetenceProfileService;
import iv1201.group7.recruitmentApp.application.CompetenceService;
import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.ApplicationDTO;
import iv1201.group7.recruitmentApp.domain.Competence;
import iv1201.group7.recruitmentApp.domain.CompetenceCreation;
import iv1201.group7.recruitmentApp.domain.CompetenceProfile;
import iv1201.group7.recruitmentApp.domain.HandleApplicationException;
import iv1201.group7.recruitmentApp.domain.UserPrincipal;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Group 7
 */

@Controller
@Scope("session")
/**
 * The class responsible for returning the correct view related to 
 * create applications.
 */
public class CreateApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private CompetenceProfileService compProfileService;
    private ApplicationDTO application;
    private ApplicantAccount currentApplicant;
    private List<Competence> competences;
    private CompetenceCreation compCreation;
    private List<Double> listOfExperience;
    
    /**
     * Handles HTTP GET requests for /create
     * @param createForm The submitted form
     * @return The create.html page.
     */
    @GetMapping("/create")
    public String createApplicationView(Model model, Locale locale) 
    {
        model = getCompetenceFromDatabase(model, locale);
        model = addEmptyYearsToList(model);
        return "create";
    }
    
    /**
     * Handles the http post request for the /create page
     * @param competenceCreation the data that has been entered in the web interface
     * @param bindingResult Validates the result
      * @param model data used by thymeleaf to render the web interface
      * @param auth The authentication token from the website
     * @return if the application is not null it returns the next site in the chain, if it is null it returns the create page itself
     */
    @PostMapping("/create")
    public String registerApplication(@ModelAttribute CompetenceCreation competenceCreation, BindingResult bindingResult, Model model, Authentication auth)
            throws HandleApplicationException
    {
        if(bindingResult.hasErrors())
        {
            return "create";
        }
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();
        currentApplicant = user.getApplicantObject();
        if(applicationService.findApplicationByApplicantId(currentApplicant.getId(), "create") == null)
        {
            listOfExperience = competenceCreation.getYearsOfExperience();
            for(int i = 0; i < listOfExperience.size(); i++)
            {
                if(listOfExperience.get(i) <= 0)
                {
                    listOfExperience.remove(i);
                    competences.remove(i);
                    i--;
                }
            }
            List<CompetenceProfile> compProfiles = compProfileService.createCompetenceProfile(listOfExperience, currentApplicant, competences);
            application = applicationService.createApplication(currentApplicant);
            return showApplication(model);
        }
        return "redirect:create";
    }
    
    private String showApplication(Model model)
    {
        if(application != null)
            model.addAttribute("viewedApplication", application);
        return "applicationfinished";
    }
    
    private Model getCompetenceFromDatabase(Model model, Locale locale)
    {
        competences = competenceService.getAllCompetencesWithTheSameLanguage(locale.getLanguage());
        compCreation = new CompetenceCreation(competences);
        model.addAttribute("competence", competences);
        return model;
    }
    
    private Model addEmptyYearsToList(Model model)
    {
        for(int i = 0; i < competences.size(); i++)
            compCreation.addYearsOfExperience(0);
        model.addAttribute("compCreation", compCreation);
        return model;
    }
}
