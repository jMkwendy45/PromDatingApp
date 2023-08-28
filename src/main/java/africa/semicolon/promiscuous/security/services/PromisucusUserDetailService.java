package africa.semicolon.promiscuous.security.services;

import africa.semicolon.promiscuous.model.User;
import africa.semicolon.promiscuous.security.models.SecureUser;
import africa.semicolon.promiscuous.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PromisucusUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user =userService.getUserByUserName(username);
      UserDetails userDetails = new SecureUser(user);
      return userDetails;
    }
}
