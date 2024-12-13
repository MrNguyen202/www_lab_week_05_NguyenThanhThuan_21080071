/*
 * @ (#) CandidateService.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.services.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.dtos.CandidateTopLevelDTO;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.Experience;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.repositories.CandidateRepository;
import vn.edu.iuh.hero.backend.repositories.CandidateSkillRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService implements IServices<Candidate, Long> {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Override
    public Candidate add(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate update(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate delete(Long aLong) {
        return candidateRepository.findById(aLong).map(candidate -> {
            candidateRepository.delete(candidate);
            return candidate;
        }).orElse(null);
    }

    @Override
    public Optional<Candidate> findById(Long aLong) {
        return Optional.of(candidateRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Iterator<Candidate> getAll(Pageable pageable) {
        return candidateRepository.findAll(pageable).iterator();
    }

    public Page<CandidateTopLevelDTO> getAllTopLevel(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Object[]> candidates = candidateRepository.findTopCandidatesByExperience(pageable);

        return candidates.map(candidateData -> {
            Candidate candidate = (Candidate) candidateData[0];
            Double totalExperience = (Double) candidateData[1];
            List<String> skills = candidateSkillRepository.findSkillsByCandidateId(candidate.getId());

            // Assuming companies can be derived from experience or another query
            List<String> companies = candidate.getExperiences()
                    .stream()
                    .map(Experience::getCompanyName)
                    .toList();

            // Calculate the level based on totalExperience (if applicable)
//            System.out.println("Total experience: " + totalExperience);
            String level = determineLevelBasedOnExperience(totalExperience);

            return new CandidateTopLevelDTO(candidate, level, companies, skills);
        });
    }

    private String determineLevelBasedOnExperience(Double totalExperience) {
        if (totalExperience < 2) return "Junior";
        if (totalExperience < 5) return "Mid-level";
        return "Senior";
    }

}
