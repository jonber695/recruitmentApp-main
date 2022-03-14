/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.domain;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Jacob Dwyer
 */
public class UserPrincipal implements UserDetails {
    private ApplicantAccount applicant;
    private RecruiterAccount recruiter;
    private boolean isRecruiter;
    
    public UserPrincipal(ApplicantAccount applicant) {
        this.applicant = applicant;
        this.isRecruiter = false;
    }
    
    public UserPrincipal(RecruiterAccount recruiter) {
        this.recruiter = recruiter;
        this.isRecruiter = true;
    }
    
    public ApplicantAccount getApplicantObject(){
        return applicant;
    }
    
    public RecruiterAccount getRecruiterObject(){
        return recruiter;
    }
    
    public boolean getRecruiterStatus(){
        return isRecruiter;
    }
    
    public void setRecruiterStatus(boolean isRecruiter){
        this.isRecruiter = isRecruiter;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }
    
    @Override
    public String getUsername(){
        if(isRecruiter)
            return recruiter.getUsername();
        
        return applicant.getUsername();
    }
    
    @Override
    public String getPassword(){
        if(isRecruiter)
            return recruiter.getPassword();
        
        return applicant.getPassword();
    }
    
    @Override
    public boolean isAccountNonExpired(){
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked(){
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return false;
    }
    
    @Override
    public boolean isEnabled(){
        return true;
    }
    
}