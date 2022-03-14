/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jonat
 */
@Entity
@Table(name = "COMPETENCE_PROFILE")
public class CompetenceProfile implements CompetenceProfileDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "COMPETENCE_PROFILE_ID")
    private int id;
    
    @Column(name = "COMPETENCE_ID")
    private int competenceId;
    
    @Column(name = "APPLICANT_ID")
    private int applicantId;
    
    @Column(name = "YEARS_OF_EXPERIENCE")
    private BigDecimal yearsOfExperience;
    
    @Override
    public int getId()
    {
        return id;
    }
    
    @Override
    public int getCompetenceId()
    {
        return competenceId;
    }
    
    @Override
    public int getApplicantId()
    {
        return applicantId;
    }
    
    @Override
    public BigDecimal getYearsOfExperience()
    {
        return yearsOfExperience;
    }
    
    public CompetenceProfile(int competencesId, int applicantsId, BigDecimal yearOfExperience)
    {
        competenceId = competencesId;
        applicantId = applicantsId;
        yearsOfExperience = yearOfExperience;
    }
    
    /**
     * Set the specified year as years of experience
     * @param year The specified year
     */
    public void setYearsOfExperience(BigDecimal year)
    {
        yearsOfExperience = year;
    }
    
    /**
     * Sets the specified applicant ID as applicant id in table
     * @param applicantId The specified applicant id
     */
    public void setApplicantId(int applicantId)
    {
        this.applicantId = applicantId;
    }
    
    /**
     * Sets the specified competence Id as competence id in table
     * @param competenceId The specified competence Id
     */
    public void setCompetenceId(int competenceId)
    {
        this.competenceId = competenceId;
    }
}
