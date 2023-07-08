package br.com.compassuol.pb.challenge.authorization.repository;

import br.com.compassuol.pb.challenge.authorization.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {
    Optional<UserCredential> findByEmail(String email);
}
