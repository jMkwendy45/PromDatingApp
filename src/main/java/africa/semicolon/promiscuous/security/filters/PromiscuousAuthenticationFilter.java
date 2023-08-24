package africa.semicolon.promiscuous.security.filters;

import africa.semicolon.promiscuous.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
@AllArgsConstructor
public class PromiscuousAuthenticationFilter  extends  UsernamePasswordAuthenticationFilter  {

    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //Extract auth credential from the request
            InputStream inputStream =request.getInputStream();
            //use the object mapper to get requet
        LoginRequest loginRequest =  objectMapper.readValue(inputStream, LoginRequest.class);
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        //2 create an auth Object that is not yet authenticated
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        //delegate authentication responsiblity of the auth object to the auth manager
         Authentication authenticationResult =   authenticationManager.authenticate(authentication);
         // put the now authenticated object in the securityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;

        } catch (IOException e) {
            throw new ProviderNotFoundException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
