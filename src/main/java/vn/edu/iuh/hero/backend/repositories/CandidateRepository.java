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
}