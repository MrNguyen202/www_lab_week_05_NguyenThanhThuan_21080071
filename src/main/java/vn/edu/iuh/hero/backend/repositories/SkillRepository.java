package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.models.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}