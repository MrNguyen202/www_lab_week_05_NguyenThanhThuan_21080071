package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.models.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}