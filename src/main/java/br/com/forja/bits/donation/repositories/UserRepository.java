package br.com.forja.bits.donation.repositories;

import br.com.forja.bits.donation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select p from User p where p.username = :username")
	Optional<User> findByUsername(String username);

    Optional<User> findByUuid(String uuid);
}
