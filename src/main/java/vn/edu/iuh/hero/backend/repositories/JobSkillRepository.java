package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.hero.backend.ids.JobSkillId;
import vn.edu.iuh.hero.backend.models.JobSkill;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    @Query("SELECT s.skillName FROM JobSkill js JOIN js.skill s WHERE js.job.id = :jobId")
    List<String> findSkillsByJobId(@Param("jobId") Long jobId);
}