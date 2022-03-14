package iv1201.group7.recruitmentApp.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Group 7
 */
@Entity
@Table(name = "RECRUITER")
/**
 * Represents a recruiter, used by JPA.
 */
public class RecruiterAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECRUITER_ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "PASSWORD")
    private String password;

    
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
     * Gets the id of the recruiter.
     * @return The id.
     */
    //@Override
    public int getId() {
        return id;
    }
    /*
    /**
     * Gets the username of the recruiter.
     * @return The username.
     */
    @Override
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the name of the recruiter.
     * @return The name.
     */
    //@Override
    public String getName() {
        return name;
    }

    /**
     * Gets the surname of the recruiter.
     * @return The surname.
     */
    /*@Override
    public String getSurname() {
        return surname;
    }*/
    
    /**
     * Gets the password of the recruiter.
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the id of the recruiter
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the recruiter
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the surname of the recruiter
     * @param surname The surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the username of the recruiter
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the recruiter
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Recruiter [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
    
}
