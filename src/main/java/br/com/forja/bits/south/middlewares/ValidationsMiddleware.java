package br.com.forja.bits.south.middlewares;

import br.com.forja.bits.south.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class ValidationsMiddleware {

    public User authenticatedUser() throws Exception {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!(principal instanceof User))
                throw new Exception("Not Authorized");

            return (User) principal;
        } catch (Exception e) {
            throw new Exception("Not Authorized");
        }
    }

}
