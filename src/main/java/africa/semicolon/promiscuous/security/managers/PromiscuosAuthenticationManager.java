package africa.semicolon.promiscuous.security.managers;

import africa.semicolon.promiscuous.exception.AuthenticationNotSupported;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationNotSupportedException;

import static africa.semicolon.promiscuous.enums.ExceptionMessage.AUTHENTICATION_NOT_SUPPORT;

@Component
@AllArgsConstructor
public class PromiscuosAuthenticationManager  implements  AuthenticationManager {
     private final AuthenticationProvider authenticationProvider;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authenticationProvider.supports(authentication.getClass())){
            Authentication authenticationResult = authenticationProvider.authenticate(authentication);
            return authenticationResult;
        }
        throw new AuthenticationException(AUTHENTICATION_NOT_SUPPORT.getMessage()) {
        };
    }
    }
