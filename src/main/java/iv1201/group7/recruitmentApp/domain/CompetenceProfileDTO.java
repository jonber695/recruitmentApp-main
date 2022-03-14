/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import java.math.BigDecimal;

/**
 *
 * @author jonat
 */
public interface CompetenceProfileDTO
{
    /**
     * Gets the Id of the competence profile object
     * @return The id of the competence profile
     */
    int getId();
    
    /**
     * Gets the applicant id of the competence profile object
     * @return The applicant Id
     */
    int getApplicantId();
    
    /**
     * Gets the competence id of the competence profile object
     * @return The competence Id
     */
    int getCompetenceId();
    
    /**
     * Gets the years of experience of the competence profile object
     * @return The years of experience
     */
    BigDecimal getYearsOfExperience();
}
