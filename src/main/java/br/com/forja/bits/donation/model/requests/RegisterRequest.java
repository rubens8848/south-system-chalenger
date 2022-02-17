package br.com.forja.bits.donation.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;


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
    @Min(6)
    private String password;

//    @NotNull
//    @NotBlank
////    @Pattern(regexp = "[+][0-9]{1,3}")
//    private String ddi;
//
//    @NotNull
//    @NotBlank
////    @Pattern(regexp = "[0-9]{8,12}", message = "invalid phone")
//    private String phone;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)([a-zà-ú]{2,30})", message = "invalid first name")
    private String firstName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)([a-zà-ú]{2,30})", message = "invalid last name")
    private String lastName;

}
