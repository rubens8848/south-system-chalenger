package br.com.forja.bits.south.services;

import br.com.forja.bits.south.enums.TokenTypes;
import br.com.forja.bits.south.model.User;
import br.com.forja.bits.south.model.requests.LoginRequest;
import br.com.forja.bits.south.model.requests.RegisterRequest;
import br.com.forja.bits.south.model.response.EntityResponses;
import br.com.forja.bits.south.model.response.LoginResponse;
import br.com.forja.bits.south.model.response.Response;
import br.com.forja.bits.south.repositories.UserRepository;
import br.com.forja.bits.south.utils.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static br.com.forja.bits.south.utils.Messages.*;

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

        return new EntityResponses().ok(user.convertToResponse());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity save(RegisterRequest registerRequest) {
        try {

            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent())
                return new Response(EMAIL_ALREADY_IN_USE).conflict();

            User user = configUser(registerRequest);
            User savedUser = userRepository.save(user);

            return new Response(savedUser.convertToResponse(), USER_CREATED).created();

        } catch (Exception e) {
            return new Response(FAIL_TO_SAVE_USER).badRequest();
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
        return new EntityResponses().ok(findUserById(user.getId()).convertToResponse());
    }

    public ResponseEntity authenticate(LoginRequest loginRequest) {
        try {
            User user = findByUsername(loginRequest.getUsername());

            if (user == null)
                return new Response("Fail to authenticate, invalid email or password").badRequest();

            if (!user.isEnabled())
                return new Response("Fail to authenticate, because User is disabled").badRequest();

            // Verify Pass
            boolean isAuthenticated = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());

            // Caso a senha nao esteja correta devolve o mesmo erro do email para proteger informações
            if (!isAuthenticated)
                return new Response("Fail to authenticate, invalid email or password").badRequest();

            String subject = user.getUuid();
            String secret = Env.getInstance().SECURITY_JWT_SECRET;
            long duration = Long.parseLong(Objects.requireNonNull(Env.getInstance().SECURITY_JWT_EXPIRATION)) * 24;

            // Generating token
            String token = tokenService.generate(TokenTypes.USER, subject, duration, secret);

            return new EntityResponses().ok(LoginResponse.builder()
                    .token(token)
                    .user(user.convertToResponse())
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
        user.setCpf(registerRequest.getCpf());
        user.setPassword(registerRequest.getPassword());
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreatedAt(new Date());

        return user;
    }


}
