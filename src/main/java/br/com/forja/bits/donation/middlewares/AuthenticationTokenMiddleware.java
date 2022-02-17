package br.com.forja.bits.donation.middlewares;

import br.com.forja.bits.donation.repositories.UserRepository;
import br.com.forja.bits.donation.services.TokenService;
import br.com.forja.bits.donation.utils.Env;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Scanner;

public class AuthenticationTokenMiddleware extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthenticationTokenMiddleware(TokenService tokenService, UserRepository playerRepository) {
        this.tokenService = tokenService;
        this.userRepository = playerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
//             printRequest(request);

            String token = Optional.of(request.getHeader("Authorization"))
                    .orElseThrow(() -> new Exception("Request without token."))
                    .replace("Bearer ", "");

            String secret = Env.getInstance().SECURITY_JWT_SECRET;

            // Validating JWT
            Optional<Claims> body = tokenService.validate(token, secret);

            if (body.isPresent()) {
                String type = body.get().getAudience();
                String uuid = body.get().getSubject();

                if (type.equalsIgnoreCase("user"))
                    setLoggedUser(uuid);
            }
        } catch (Exception e) {
        } finally {
            configureCors(response);
            filterChain.doFilter(request, response);
        }
    }

    private void setLoggedUser(String uuid) {
        userRepository.findByUuid(uuid)
                .ifPresent(admin -> setLogged(new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities())));
    }

    private void setLogged(Authentication authenticated) {
        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }


    private void configureCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, HEAD, OPTIONS, POST, PUT, SEARCH, UPDATE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
    }

}
