/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.config;

import iv1201.group7.recruitmentApp.application.SecurityUserDetailsService;
import iv1201.group7.recruitmentApp.domain.UserPrincipal;
import iv1201.group7.recruitmentApp.repository.ApplicantAccountRepository;
import iv1201.group7.recruitmentApp.repository.RecruiterAccountRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A custom AuthenticationProvider which handles authentication of RECRUITER and
 *      APPLICANT users of the app.
 * @author Group 7
 */
@Component
public class CustomAuthProvider implements AuthenticationProvider {
    @Autowired
    private SecurityUserDetailsService userDetailsService;
    
    /**
     * Creates a new BCryptPasswordEncoder object which can encode and check if
     *     a raw password matches an encoded string
     * @return a new BCryptPasswordEncoder object
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    public CustomAuthProvider() {
        super();
    }
    
    /**
     * Attempts to authenticate the passed Authentication object, returning a fully
     *      populated Authentication object upon success and throws an exception
     *      upon failure.
     * @param auth The authentication request object
     * @return A fully authenticated object
     * @throws AuthenticationException thrown if the authentication fails
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String usernameAndIsRecruiter = String.valueOf(auth.getName());
        UserPrincipal user = userDetailsService.loadUserByUsername(usernameAndIsRecruiter);
        String password = String.valueOf(auth.getCredentials().toString());

        if (user.getRecruiterStatus()) {
            if (user != null && passwordEncoder().matches(password, user.getPassword())) {

                final List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_RECRUITER"));
                final Authentication authentication = new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
                user = null;
                return authentication;
            } else {
                throw new BadCredentialsException("Bad Credentials");
            }
        } else {
            if (user != null && passwordEncoder().matches(password, user.getPassword())) {

                final List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_APPLICANT"));
                final Authentication authentication = new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
                user = null;
                return authentication;
            } else {
                throw new BadCredentialsException("Bad Credentials");
            }
        }
    }
    
    /**
     * Returns true if this AuthenticationProvider supports closer evaluation of
     *      the given Authentication class.
     * @param authentication The Authentication class to check support of
     * @return true if presented Authentication class can be more closely evaluated
     *      by the implementation
     */
    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}