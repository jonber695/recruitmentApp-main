/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

/**
 *
 * @author Group 7
 */
public interface ApplicantAccountDTO {
    
    /**
     * Gets the id of the applicant. 
     */
    int getId();
    /**
     * Gets the (first) name of the applicant.
     */
    String getName();

    /**
     * Gets the surname of the applicant.
     */
    String getSurname();
    
    /**
     * Gets the personal number of the applicant.
     */
    String getPnr();
    
    /**
     * Gets the email of the applicant.
     */
    String getEmail();
    
    /**
     * Gets the password of the applicant.
     */
    String getPassword();
    
}
