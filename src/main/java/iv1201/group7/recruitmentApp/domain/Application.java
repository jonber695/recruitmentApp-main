/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

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
@Table(name = "APPLICATION")
public class Application implements ApplicationDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "APPLICATION_ID")
    private int id;
    
    @Column(name = "APPLICANT_ID")
    private int applicantId;
    
    @Column(name = "ACCEPTED")
    private Boolean accepted;
    
    @Column(name = "RECRUITER_ID")
    private Integer recruiterId;
    
    @Column(name = "VERSION")
    private int version;
    
    
    
    /**
     * Gets the applicationId
     * @return the Application ID
     */
    @Override
    public int getId()
    {
        return id;
    }
    
    /**
     * Gets the application ID for the application in question
     * @return  The application ID
     */
    @Override
    public int getApplicantId()
    {
        return applicantId;
    }
    
    /**
     * Gets the status of the application, true if it is accepted,
     * false if it is denied or null if it has not been checked yet
     * @return  The status of the application
     */
    @Override
    public Boolean getAccepted()
    {
        return accepted;
    }
    
    /**
     * Gets the id of the recruiter that has last edited the application,
     * or null if it has not been edited yet
     * @return The id of the recruiter
     */
    @Override
    public Integer getRecruiterId()
    {
        return recruiterId;
    }
    
    @Override
    public int getVersion(){
        return version;
    }
    
    /**
     * Sets the id of the specific application
     * @param id The application ID
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * Sets the id of the applicant in the specific application
     * @param id The applicants ID
     */
    public void setApplicantId(int id)
    {
        this.applicantId = id;
    }
    
    /**
     * Sets the boolean accepted according to the paramater accepted
     * @param accepted the parameter accepted
     */
    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }
    
    /**
     * Sets the recreuiter id from the specified recruiter id
     * @param recruiterId The specified recruiter Id
     */
    public void setRecruiterId(int recruiterId)
    {
        this.recruiterId = recruiterId;
    }
    
    public void setVersion(int version){
        this.version = version;
    }
    
    @Override
    public String toString(){ // default: id=0, applicant id=0, accepted=null, recruiter id=null, version=0
        return "Application [id=" + id + ", applicant id=" + applicantId + ", accepted=" + accepted 
                + ", recruiter id=" + recruiterId + ", version=" + version + "]";
    }
}
