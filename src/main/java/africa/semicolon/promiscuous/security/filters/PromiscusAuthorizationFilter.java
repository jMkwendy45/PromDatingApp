package africa.semicolon.promiscuous.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static africa.semicolon.promiscuous.utils.AppUtils.getpublicPath;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class PromiscusAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (getpublicPath().contains(request.getServletPath()))filterChain.doFilter(request,response);
        authorize(request);

    }
    public void authorize(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String token =authorizationHeader.substring("Bearer".length());
        extractClaimsFrom(token);
    }

    private void extractClaimsFrom(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Claim claim = decodedJWT.getClaim("roles");
    }
}
