/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

/**
 *
 * @author jonat
 */
public interface ApplicationDTO
{
    
    /**
     * Gets the application ID
     */
    int getId();
    
    /**
     * Gets the id of the applicant from the application in question
     */
    int getApplicantId();
    
    /**
     * Gets the status of the application, true if it is accepted,
     * false if it is denied and null if it has not been checked yet
     */
    Boolean getAccepted();
    
    /**
     * Gets the last recruiter to have changed the application,
     * or null if it has not been checked yet
     */
    Integer getRecruiterId();
    
    /**
     * Gets the version of the application.
     */
    int getVersion();
}
