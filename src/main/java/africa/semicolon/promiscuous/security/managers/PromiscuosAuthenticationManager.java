package africa.semicolon.promiscuous.security.managers;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PromiscuosAuthenticationManager  implements  AuthenticationManager {
     private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

          Authentication aunthecationresult=      authenticationProvider.authenticate(authentication);
             return aunthecationresult;
    }

}
