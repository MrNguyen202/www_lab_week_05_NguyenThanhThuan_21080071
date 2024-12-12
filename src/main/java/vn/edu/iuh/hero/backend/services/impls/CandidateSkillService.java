/*
 * @ (#) CandidateSkillService.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.ids.CandidateSkillId;
import vn.edu.iuh.hero.backend.models.CandidateSkill;
import vn.edu.iuh.hero.backend.repositories.CandidateSkillRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */
@Service
public class CandidateSkillService implements IServices<CandidateSkill, CandidateSkillId> {

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Override
    public CandidateSkill add(CandidateSkill candidateSkill) {
        return candidateSkillRepository.save(candidateSkill);
    }

    @Override
    public CandidateSkill update(CandidateSkill candidateSkill) {
        return candidateSkillRepository.save(candidateSkill);
    }

    @Override
    public CandidateSkill delete(CandidateSkillId candidateSkillId) {
        return candidateSkillRepository.findById(candidateSkillId).map(candidateSkill -> {
            candidateSkillRepository.delete(candidateSkill);
            return candidateSkill;
        }).orElse(null);
    }

    @Override
    public Optional<CandidateSkill> findById(CandidateSkillId candidateSkillId) {
        return Optional.of(candidateSkillRepository.findById(candidateSkillId)).orElse(null);
    }

    @Override
    public List<CandidateSkill> findAll() {
        return candidateSkillRepository.findAll();
    }
}
