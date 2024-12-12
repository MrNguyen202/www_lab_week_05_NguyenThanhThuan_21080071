package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.models.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}