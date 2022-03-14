/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccountDTO;
import iv1201.group7.recruitmentApp.domain.Application;
import iv1201.group7.recruitmentApp.domain.ApplicationDTO;
import iv1201.group7.recruitmentApp.domain.HandleApplicationException;
import iv1201.group7.recruitmentApp.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jonat
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)

public class ApplicationService
{
    @Autowired
    private ApplicationRepository applicationRepo;


    /**
     * Finds an application by ID
     * @param id the id of the requested application
     * @return An ApplicationDTO with the attributes of the found application,
     * or null if no such application exists.
     */
    public ApplicationDTO findApplicationById(int id)
    {
        return applicationRepo.findApplicationById(id);
    }

    /**
     * Finds an application by applicant ID
     * @param id the id of the requested applicant
     * @param component A string representing which component calls the method to get the right exception handling
     * @return An ApplicationDTO with the attribures of the found application,
     * or null if no such application exists.
     *      * @throws TODO
     */
    public ApplicationDTO findApplicationByApplicantId(int id, String component) throws HandleApplicationException
    {
        Application application = applicationRepo.findApplicationByApplicantId(id);
            if (application == null && component.equals("search")){
                throw new HandleApplicationException("There is no application for this applicant");
            }
            else if(application != null && component.equals("create"))
                throw new HandleApplicationException("There is already an application for this applicant");
        return applicationRepo.findApplicationByApplicantId(id);
    }

    /**
     * Creates an application with the id of the specified applicant
     * @param applicant The specified applicant
     * @return  The newly created Application
     */
    public ApplicationDTO createApplication(ApplicantAccountDTO applicant)
    {
        Application application = new Application();
        application.setApplicantId(applicant.getId());
        return applicationRepo.save(application);
    }

    public void updateApplication(Application application) {
        applicationRepo.save(application);
    }

    /**
     * Updates an application when a recruiter accepts or rejects it.
     *
     * @param handledApplication The application to update or reject.
     * @param status True for accepted, false for rejected.
     * @param recruiterId The ID of the recruiter.
     * @param version The current version of the application in the database.
     * @param application The ApplicationDTO object (to be converted into an
     * Application object).
     */
    public void updateApplication(Application handledApplication,
            boolean status, int recruiterId, ApplicationDTO application, int version)
            throws HandleApplicationException {
        ApplicationDTO isUpdated = findApplicationByApplicantId(application.getApplicantId(), "search");
        if(isUpdated.getVersion() != version){
            throw new HandleApplicationException("This applicant has already been processed");
        }
        handledApplication.setId(application.getId());
        handledApplication.setApplicantId(application.getApplicantId());
        handledApplication.setAccepted(status);
        handledApplication.setRecruiterId(recruiterId); //This needs changing to actual recruiter ID
        handledApplication.setVersion(version + 1);

        applicationRepo.save(handledApplication);
    }
}
