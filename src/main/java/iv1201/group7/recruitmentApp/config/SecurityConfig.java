/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iv1201.group7.recruitmentApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Used for configuring authentication mechanisms for the application.
 * @author Group 7
 */
@Configuration
@EnableWebSecurity//(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthProvider customAuth;
    
    //@Autowired
    //private CustomAuthFilter customAuthFilter;
    
    @Autowired
    private UrlAuthSuccessHandler urlAuthSuccessHandler;
    
    //@Autowired
    //private AuthFailureHandler authFailureHandler;
    @Bean
    public AuthFailureHandler failureHandler(){
        AuthFailureHandler failureHandler = new AuthFailureHandler();
        failureHandler.setDefaultFailureUrl("/login?error=true");
        return failureHandler;
    }

    /*
    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }*/

    /*@Bean
    public UrlAuthSuccessHandler authSuccessHandler() {
        return new UrlAuthSuccessHandler();
    }*/

    // this is needed to pass the authentication manager into our custom security filter
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public CustomAuthFilter authenticationFilter() throws Exception {
        CustomAuthFilter filter = new CustomAuthFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationFailureHandler(failureHandler());
        filter.setAuthenticationSuccessHandler(urlAuthSuccessHandler);
        return filter;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuth);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                //.antMatchers("/**").permitAll()
                .antMatchers("/login", "/login?error=true").permitAll() // unnecessary??
                .antMatchers("/create").hasRole("APPLICANT")
                .antMatchers("/search", "/searchresults", "/viewapplication", "/accepted", 
                        "/rejected").hasRole("RECRUITER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")//.usernameParameter("username").permitAll()
                //.successHandler(authSuccessHandler)//myAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
                //.failureHandler(failureHandler())
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)//.permitAll()
                ;
        
        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
