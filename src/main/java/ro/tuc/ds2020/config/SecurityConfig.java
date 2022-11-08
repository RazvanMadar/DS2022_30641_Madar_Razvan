package ro.tuc.ds2020.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.security.jwt.JwtFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserRepository userRepository, JwtFilter jwtFilter) {
        this.userRepository = userRepository;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!")));
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/roles").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/assign-device/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/devices").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/devices").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/devices/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/devices/*").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/devices/free").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/devices/unassigned/**").hasAnyAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/devices/consumptions/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/users/*/devices").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/login", "/consumptions/**").permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}


