package br.com.forja.bits.donation.controller;

import br.com.forja.bits.donation.model.requests.RegisterRequest;
import br.com.forja.bits.donation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity save(@RequestBody @Valid RegisterRequest registerRequest) {
		return userService.save(registerRequest);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity findById(@PathVariable Integer id) {
		return userService.findById(id);
	}

}
