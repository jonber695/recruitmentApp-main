package iv1201.group7.recruitmentApp.presentation.accounts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Group 7
 */
public class LoginForm {
    @NotBlank(message = "{login.username.missing}")
    @Size(min=6, max=50, message = "{login.username.size}")
    private String username;
    @NotBlank(message = "{login.password.missing}")
    @Size(min=6, max=50, message = "{login.password.size}")
    private String password;
    private boolean loginAsRecruiter = false;

    /**
     * Gets the username of the user.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username The username of the searched account.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the password of the user.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password The password of the searched account.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getLoginAsRecruiter() {
        return loginAsRecruiter;
    }
    
    public void setLoginAsRecruiter(boolean loginAsRecruiter) {
        this.loginAsRecruiter = loginAsRecruiter;
    }
}
