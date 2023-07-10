package br.com.compassuol.pb.challenge.notification.repository;

import br.com.compassuol.pb.challenge.notification.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {

}
