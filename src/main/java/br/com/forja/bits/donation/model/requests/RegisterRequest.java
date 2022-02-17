package br.com.forja.bits.donation.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @NotNull
    @NotBlank
    @Email
    private String username;

    @NotNull
    @NotBlank
    private String password;

}
