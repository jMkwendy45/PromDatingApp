package africa.semicolon.promiscuous.security.managers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PromiscuosAuthenticationManager  implements  AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
