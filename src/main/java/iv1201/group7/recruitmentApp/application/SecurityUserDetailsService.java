/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.application;

import iv1201.group7.recruitmentApp.domain.ApplicantAccount;
import iv1201.group7.recruitmentApp.domain.RecruiterAccount;
import iv1201.group7.recruitmentApp.domain.UserPrincipal;
import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;
import iv1201.group7.recruitmentApp.repository.RecruiterAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

/**
 * Class used for loading user-specific data from the database
 * @author Group 7
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class SecurityUserDetailsService implements UserDetailsService { 
   @Autowired 
   private ApplicantAccountRepository applicantAccountRepo;
   @Autowired 
   private RecruiterAccountRepository recruiterAccountRepo;
   
   
   /**
    * Loads and returns user information from the database based on the username. 
    *       Returns null if no user with a matching username is found.
    * @param username The username of the user whose information should be obtained
    *       from the database
    * @return
    * @throws UsernameNotFoundException if no user with a matching username is
    *       found
    */
   @Override 
   public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameAndIsRecruiter = username.split("#####");
                //StringUtils.split(username, String.valueOf(Character.LINE_SEPARATOR));
        if (usernameAndIsRecruiter == null || usernameAndIsRecruiter.length != 2) {
            throw new UsernameNotFoundException("Username and choice must be provided");
        }
        boolean isRecruiter = Boolean.parseBoolean(usernameAndIsRecruiter[1]);
        if(isRecruiter){
            RecruiterAccount recruiter = recruiterAccountRepo.findRecruiterByUsername(usernameAndIsRecruiter[0]);
            if(recruiter==null)
                throw new UsernameNotFoundException("404: No Recruiter with those credentials found");
            //.orElseThrow(() -< new UsernameNotFoundException("User not present"));
             return new UserPrincipal(recruiter);//new UserPrincipal(recruiter); 
        } else if(!isRecruiter) {
            ApplicantAccount applicant = applicantAccountRepo.findApplicantByEmail(usernameAndIsRecruiter[0]);
            if(applicant==null)
                throw new UsernameNotFoundException("404: No Applicant with those credentials found");
            //.orElseThrow(() -< new UsernameNotFoundException("User not present"));
            return new UserPrincipal(applicant);//new UserPrincipal(recruiter); 
        }
        
        return null;
   } 
}