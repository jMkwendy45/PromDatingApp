package africa.semicolon.promiscuous.security;

import africa.semicolon.promiscuous.security.filters.PromiscuousAuthenticationFilter;
import africa.semicolon.promiscuous.security.filters.PromiscusAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static africa.semicolon.promiscuous.model.Role.CUSTOMER;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        var authenticationFilter = new PromiscuousAuthenticationFilter(authenticationManager);
//        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .sessionMaagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(new PromiscusAuthorizationFilter(), PromiscuousAuthenticationFilter.class)
//                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(c->c.requestMatchers(POST, "/api/v1/user","/login")
//                        .permitAll())
//                .authorizeHttpRequests(c->c.requestMatchers(PUT, "/api/v1/user/**")
//                        .hasRole(CUSTOMER.name()))
//                .authorizeHttpRequests(c->c.anyRequest().authenticated())
//                .build();
//    }
        return null;

    }
}