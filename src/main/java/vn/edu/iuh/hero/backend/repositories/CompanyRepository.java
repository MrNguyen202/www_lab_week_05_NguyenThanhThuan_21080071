package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}