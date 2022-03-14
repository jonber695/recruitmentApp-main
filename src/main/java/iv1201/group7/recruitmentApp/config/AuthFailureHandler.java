/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Group 7
 */
@Component
public class AuthFailureHandler 
   extends SimpleUrlAuthenticationFailureHandler 
// implements AuthenticationFailureHandler
{
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //public static final String LAST_USERNAME_KEY = "LAST_USERNAME";
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
            HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
        //redirectStrategy.sendRedirect(request, response, "/login?error=true");
        
        // this works!!
        //request.getSession().setAttribute(LAST_USERNAME_KEY, request.getParameter("username"));
        //super.onAuthenticationFailure(request, response, exception);
        
        /* For redirecting to a printout of the exception with timestamp
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
          "timestamp", 
          Calendar.getInstance().getTime());
        data.put(
          "exception", 
          exception.getMessage());

        response.getOutputStream()
          .println(objectMapper.writeValueAsString(data));
        System.out.println(objectMapper.writeValueAsString(data));*/
        
        super.onAuthenticationFailure(request, response, exception);
    }
    
}
