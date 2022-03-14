/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * A custom UsernamePasswordAuthenticationFilter implementing support of the checkbox
 *      element 'loginAsRecruiter'
 * @author Group 7
 */
//@Component
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
    
    @Autowired
    public CustomAuthProvider authProvider;
    
    public CustomAuthFilter() {
        super();
    }
    
    //@Autowired
    //@Qualifier("authenticationManager")
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    
    /**
     * Performs the actual authentication. Returns a populated authentication token
     *      for the user upon successful authentication or null to indicate that authentication
     *      is still in progress. Throws exception on failure.
     * @param request The HTTP request
     * @param response The HTTP response
     * @return populated authentication token or null
     * @throws AuthenticationException Thrown if authentication process fails
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, 
      HttpServletResponse response) 
        throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        
        return authProvider.authenticate(authRequest);//this.getAuthenticationManager().authenticate(authRequest);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(
      HttpServletRequest request) {
 
        String username = obtainUsername(request);
        //username = username.substring(0, username.length()-2);
        String password = obtainPassword(request);
        String loginAsRecruiter = request.getParameter("loginAsRecruiter");

        String usernameLoginAsRecruiter = String.format("%s%s%s", username.trim(), 
          /*String.valueOf(Character.LINE_SEPARATOR)*/ "#####", loginAsRecruiter);
        return new UsernamePasswordAuthenticationToken(
          usernameLoginAsRecruiter, password);
    }
}
