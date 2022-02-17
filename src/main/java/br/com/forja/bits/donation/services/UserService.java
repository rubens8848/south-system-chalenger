package br.com.forja.bits.donation.services;

import br.com.forja.bits.donation.enums.TokenTypes;
import br.com.forja.bits.donation.model.User;
import br.com.forja.bits.donation.model.requests.LoginRequest;
import br.com.forja.bits.donation.model.requests.RegisterRequest;
import br.com.forja.bits.donation.model.response.EntityResponses;
import br.com.forja.bits.donation.model.response.LoginResponse;
import br.com.forja.bits.donation.repositories.UserRepository;
import br.com.forja.bits.donation.utils.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;


    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(EntityResponses::new);
    }

    public ResponseEntity findById(Integer id) {
        User user = this.findUserById(id);

        return new EntityResponses().ok(user.converToResponse());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity save(RegisterRequest registerRequest) {
        try {

            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent())
               return new EntityResponses().conflict("Email already in use");

            User user = configUser(registerRequest);
            User savedUser = userRepository.save(user);

            return new EntityResponses().created(savedUser.converToCreationResponse());
        } catch (Exception e) {
            return new EntityResponses().buildError("failt to save User");
        }

    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public ResponseEntity getResponseByLoggedUser() {
        // Pegar usuario pelo jwt passado na requisição
        User user = getLoggedUser();

        // Verificar se o jwt passado nao é válido
        if (user == null)
            return new EntityResponses().buildError("JWT inválido");

        // Return userResponse
        return new EntityResponses().ok(findUserById(user.getId()).converToResponse());
    }

    public ResponseEntity authenticate(LoginRequest loginRequest) {
        try {
            User user = findByUsername(loginRequest.getUsername());

            if (user == null)
                return new EntityResponses().buildError("Fail to authenticate, error: User not found.");

            if (!user.isEnabled())
                return new EntityResponses().buildError("Fail to authenticate, because User is disabled");

            // Verify Pass
            boolean isAuthenticated = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());

            // Caso a senha nao esteja correta
            if (!isAuthenticated)
                return new EntityResponses().loginError();

            String subject = user.getUuid();
            String secret = Env.getInstance().SECURITY_JWT_SECRET;
            long duration = Long.parseLong(Env.getInstance().SECURITY_JWT_EXPIRATION) * 24;

            // Generating token
            String token = tokenService.generate(TokenTypes.USER, subject, duration, secret);

            return new EntityResponses().ok(LoginResponse.builder()
                    .token(token)
                    .user(user.converToCreationResponse())
                    .build());
        } catch (Exception e) {
            return new EntityResponses().loginError();
        }
    }


    public User getLoggedUser() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    private User configUser(RegisterRequest registerRequest) {

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreatedAt(new Date());

        return user;
    }


}
