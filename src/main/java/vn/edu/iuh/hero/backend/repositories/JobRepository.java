package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.hero.backend.models.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j JOIN FETCH j.company c")
    Page<Job> findAllJobsWithCompany(Pageable pageable);

    @Query("SELECT DISTINCT j FROM Job j " +
            "JOIN JobSkill js ON js.id.jobId = j.id " +
            "JOIN CandidateSkill cs ON cs.id.skillId = js.id.skillId " +
            "WHERE cs.id.canId = :candidateId")
    Page<Job> findJobsByCandidateId(@Param("candidateId") Long candidateId, Pageable pageable);
}