package africa.semicolon.promiscuous.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static africa.semicolon.promiscuous.utils.AppUtils.APP_NAME;
import static africa.semicolon.promiscuous.utils.AppUtils.getpublicPath;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class PromiscusAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (getpublicPath().contains(request.getServletPath()))filterChain.doFilter(request,response);
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            String token = authorizationHeader.substring("Bearer ".length());
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret"))
                    .withIssuer(APP_NAME)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles")
                    .asList(SimpleGrantedAuthority.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
//    private void authorize(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        String token = authorizationHeader.substring("Bearer ".length());
//
//        List<SimpleGrantedAuthority> authorities = extractClaimsFrom(token);
//
//
//    private List<SimpleGrantedAuthority> extractClaimsFrom(String token) {
//        DecodedJWT decodedJWT = JWT.decode(token);
//        Claim claim  = decodedJWT.getClaim("roles");
//        return claim.asList(SimpleGrantedAuthority.class);
//    }
}
