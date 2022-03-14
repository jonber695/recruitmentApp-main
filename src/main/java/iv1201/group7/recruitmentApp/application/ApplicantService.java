/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.ApplicantAccountDTO;
import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Group 7
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
/**
 * Provides methods used by the Applicant domain.
 */
public class ApplicantService {
    @Autowired
    private ApplicantAccountRepository applicantAccountRepo;
    
    /**
     *
     * Finds an applicant with the specified email and password.
     * 
     * @param email The specified email.
     * @param password The specified password.
     * @return A ApplicantAccountDTO with the attributes of the found user, or 
     * null if the user is not found.
     * 
     */
    public UserDetails findApplicant(String email, String password) {
        UserDetails applicantAcct = applicantAccountRepo.findApplicantByEmail(email);
        if (applicantAcct == null || !(applicantAcct.getPassword().equals(password))){
            return null;
        }
        return applicantAcct;
    }
    
    public ArrayList<ApplicantAccount> findApplicantsBySurname(String surname) {
        ArrayList<ApplicantAccount> applicantList = new ArrayList<ApplicantAccount>();
        applicantList = applicantAccountRepo.findMultipleApplicantsBySurname(surname);
        return applicantList;
    }

    public UserDetails findApplicantBySurname(String surname) {
        return applicantAccountRepo.findApplicantBySurname(surname);
    }
}
