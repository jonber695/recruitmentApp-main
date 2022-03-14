package iv1201.group7.recruitmentApp.domain;

/**
 * @author Group 7
 */
public interface RecruiterAccountDTO {
    /**
     * Gets the id of the recruiter.
     */
    int getId();
    
     /**
     * Gets the username of the recruiter.
     */
    String getUsername();

    /**
     * Gets the name of the recruiter.
     */
    String getName();
    
    /**
     * Gets the surname of the recruiter.
     */
    String getSurname();
    
    /**
     * Gets the password of the recruiter.
     */
    String getPassword();
}
