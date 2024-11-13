package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.hero.backend.ids.CandidateSkillId;
import vn.edu.iuh.hero.backend.models.CandidateSkill;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
}