package africa.semicolon.promiscuous.security;

import africa.semicolon.promiscuous.security.filters.PromiscuousAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static africa.semicolon.promiscuous.model.Role.CUSTOMER;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private  final AuthenticationManager authenticationManager;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //...
                .addFilterAt(new PromiscuousAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
//                .authorizeHttpRequests(customizer->customizer.anyRequest().permitAll())
                .sessionManagement(customizer->customizer.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(customizer-> customizer.requestMatchers(POST,"api/v1/user").permitAll()
                ).authorizeHttpRequests(customize->customize.requestMatchers(POST,"api/vi/user/upoadMedia")
                                .hasRole(CUSTOMER.name()
                                )

                        )

                .build();
    }



}
