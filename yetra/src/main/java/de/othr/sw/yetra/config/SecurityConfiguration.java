package de.othr.sw.yetra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//TODO: should one use @Secured annotation?
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityUtilities securityUtilities;

    private BCryptPasswordEncoder passwordEncoder() {
        return securityUtilities.passwordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //TODO: enable csrf in production
            // see: https://www.baeldung.com/csrf-stateless-rest-api
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/" ).permitAll()
                .antMatchers(HttpMethod.GET, "/orders/**", "/api/orders/**").hasAuthority("ORDERS_READ")
                .antMatchers(HttpMethod.POST, "/orders/**", "/api/orders/**").hasAuthority("ORDERS_WRITE")
                .antMatchers(HttpMethod.GET, "/shares/**", "/api/shares/**").hasAuthority("SHARES_READ")
                .antMatchers(HttpMethod.POST, "/shares/**").hasAuthority("SHARES_WRITE")
                .antMatchers(HttpMethod.GET, "/employees/**", "/trading-partners/**").hasAuthority("USERS_READ")
                .antMatchers(HttpMethod.POST, "/employees/**", "/trading-partners/**").hasAuthority("USERS_WRITE")
                .antMatchers(HttpMethod.GET, "/transactions/**").hasAuthority("TRANSACTIONS_READ")
                .anyRequest().denyAll()
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/shares")
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
            .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
