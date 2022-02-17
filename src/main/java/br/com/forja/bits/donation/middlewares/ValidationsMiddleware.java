package br.com.forja.bits.donation.middlewares;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
