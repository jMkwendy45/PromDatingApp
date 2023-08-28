package africa.semicolon.promiscuous.security.providers;

import lombok.AllArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static africa.semicolon.promiscuous.enums.ExceptionMessage.INVALID_CREDENTIAL_EXCEPTION;

@Component
@AllArgsConstructor
public class PromiscusosAuthenticationProvider  implements     AuthenticationProvider{
   private final UserDetailsService userDetailsService;
   private final PasswordEncoder passwordEncoder;


   // take the username from the request
    //if the user 1 os found,use password encoder to compare from the request to users
    // if the password match request in aunthenticadted
   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       // 1. take the username from the request (contained in authentication) and use
       // the userDetailsService to look for a user from the Db with that username
       String email = authentication.getPrincipal().toString();
       UserDetails user = userDetailsService.loadUserByUsername(email);
       //2. If user from 1 is found, use the PasswordEncoder to compare the password from the
       //request to the users password from the Db
       String password = authentication.getCredentials().toString();
       boolean isValidPasswordMatch = passwordEncoder.matches(password, user.getPassword());
       //3. If the passwords match, request is authenticated
       if (isValidPasswordMatch) {
           Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
           Authentication authenticationResult = new UsernamePasswordAuthenticationToken(email, password, authorities);
           //return Authentication
           return  authenticationResult;
       }
       //4. else, request isn't authenticated
       throw new BadCredentialsException(INVALID_CREDENTIAL_EXCEPTION.getMessage());
   }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
