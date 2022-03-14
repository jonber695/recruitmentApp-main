package iv1201.group7.recruitmentApp.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Group 7
 */
@Entity
@Table(name = "APPLICANT")
/**
 * Represents an applicant, used by JPA.
 */
public class ApplicantAccount implements UserDetails, ApplicantAccountDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "APPLICANT_ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;
    
    @Column(name = "PNR")
    private String pnr;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;
    
    public ApplicantAccount(String name, String surname, 
            String pnr, String email, String password) {
       this.name = name;
       this.surname = surname;
       this.pnr = pnr;
       this.email = email;
       this.password = password;
    } //Should this be a DTO thing??
    
    public ApplicantAccount(int id)
    {
        this.id = id;
    }

    protected ApplicantAccount() {}
    
    ///////////////////////
    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("RECRUITER"));
        return authorities;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
    //////////////////////////////////
    
    /**
     * Gets the id of the applicant.
     * @return The id of the applicant.
     */
    //@Override
    public int getId(){
           return id;
    }
    
    /**
     * Gets the name of the applicant.
     * @return The name.
     */
    //@Override
    public String getName() {
        return name;
    }

    /**
     * Gets the surname of the applicant.
     * @return The surname.
     */
    //@Override
    public String getSurname() {
        return surname;
    }
    
    /**
     * Gets the personal number of the applicant.
     * @return The personal number.
     */
    //@Override
    public String getPnr(){
        return pnr;
    }
    
    /**
     * Gets the email of the applicant.
     * @return The email.
     */
    //@Override
    public String getEmail(){
        return email;
    }
    
    /**
     * Gets the password of the applicant's account.
     * @return The password.
     */
    @Override
    public String getPassword(){
        return password;
    }
    
    /**
     * Gets the username of the recruiter.
     * @return The username.
     */
    @Override
    public String getUsername() {
        return email;
    }
    
 public void setId(int id){
     this.id = id;
 }
}
