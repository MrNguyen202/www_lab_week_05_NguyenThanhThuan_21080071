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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.repositories.CandidateRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateService implements IServices<Candidate, Long> {

    @Autowired
    private CandidateRepository candidateRepository;

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
}
