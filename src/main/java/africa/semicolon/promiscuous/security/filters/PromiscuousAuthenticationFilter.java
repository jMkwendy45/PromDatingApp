package africa.semicolon.promiscuous.security.filters;

import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static africa.semicolon.promiscuous.utils.JwtUtils.generateAccessToken;
import static africa.semicolon.promiscuous.utils.JwtUtils.generateVerificationToken;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class PromiscuousAuthenticationFilter  extends  UsernamePasswordAuthenticationFilter  {

    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //Extract auth credential from the request
            InputStream inputStream =request.getInputStream();
            //use the object mapper to get request
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
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
                                            throws IOException, ServletException {
        Collection<? extends GrantedAuthority>userAuthorities = authResult.getAuthorities();
        List<? extends GrantedAuthority>authorities = new ArrayList<>();
//       String email =authResult.getPrincipal().toString();
       String token =   generateAccessToken(authorities);
      var apiResponse =  ApiResponse.builder().data(token);
      response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().print(apiResponse);
    }
}
