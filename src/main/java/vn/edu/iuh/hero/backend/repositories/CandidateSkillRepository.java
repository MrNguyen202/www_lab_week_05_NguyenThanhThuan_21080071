package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.hero.backend.ids.CandidateSkillId;
import vn.edu.iuh.hero.backend.models.CandidateSkill;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    @Query("SELECT s.skillName FROM CandidateSkill cs JOIN cs.skill s WHERE cs.can.id = :candidateId")
    List<String> findSkillsByCandidateId(@Param("candidateId") Long candidateId);
}