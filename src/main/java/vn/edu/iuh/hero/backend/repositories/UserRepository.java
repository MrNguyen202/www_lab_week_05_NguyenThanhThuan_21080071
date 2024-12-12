package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
