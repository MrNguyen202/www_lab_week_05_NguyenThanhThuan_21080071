package vn.edu.iuh.hero.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.hero.backend.models.Candidate;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    //Lấy danh sách 100 ứng viên  theo so nam kiem nghiem giảm dần
    @Query("""
                SELECT c, SUM(DATEDIFF(e.toDate, e.fromDate) / 365.0) AS totalExperience
                FROM Candidate c
                JOIN Experience e ON c.id = e.candidate.id
                GROUP BY c.id
                ORDER BY totalExperience DESC
            """)
    Page<Object[]> findTopCandidatesByExperience(Pageable pageable);
    //Lấy danh sách ứng viên phù hợp với skill của công việc
    @Query("""
                SELECT c, COUNT(DISTINCT s.skillName) AS totalMatchedSkills
                FROM Candidate c
                JOIN CandidateSkill cs ON c.id = cs.can.id
                JOIN JobSkill js ON cs.skill.id = js.skill.id
                JOIN Skill s ON js.skill.id = s.id
                WHERE js.job.id = :jobId
                GROUP BY c.id
                ORDER BY totalMatchedSkills DESC
            """)
    Page<Object[]> findCandidatesByJobId(Long jobId, Pageable pageable);
}