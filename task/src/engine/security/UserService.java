package engine.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService {
    @Resource
    private UserRepository userRepo;
    @Resource
    private PasswordEncoder encoder;

    public User findUserByEmail(String email) throws ResponseStatusException {
        return userRepo.findById(email.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void registerUser(User user) {
        Optional<User> optionalUser = userRepo.findById(user.getEmail());
        if(optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userRepo.save(new User(user.getEmail().toLowerCase(), encoder.encode(user.getPassword())));
    }

}
