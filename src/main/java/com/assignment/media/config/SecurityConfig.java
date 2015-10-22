package com.assignment.media.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();
    }

    // parse user detials
    private List<UserDetails> parseUserDetails() throws Exception {
        final List<String> credentials = Files.readAllLines(Paths.get(System.getProperty("media.credentials.source", "src/main/resources"), "application-users.properties"));
        final List<UserDetails> userDetailsList = new ArrayList<UserDetails>(credentials.size());
        for (String credential : credentials) {
            String[] credentialTokens = credential.split("\\s*,\\s*");
            userDetailsList.add(new User(credentialTokens[0], credentialTokens[1], Arrays.asList(new SimpleGrantedAuthority(credentialTokens[2]))));
        }
        return userDetailsList;
    }

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        final List<UserDetails> userDetailsList = parseUserDetails();
        authenticationManagerBuilder.userDetailsService(new InMemoryUserDetailsManager(userDetailsList));
    }
}
