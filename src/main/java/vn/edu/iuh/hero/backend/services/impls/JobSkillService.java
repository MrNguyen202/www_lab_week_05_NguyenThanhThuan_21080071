/*
 * @ (#) JobSkillService.java    1.0    11/13/2024
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
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.ids.JobSkillId;
import vn.edu.iuh.hero.backend.models.JobSkill;
import vn.edu.iuh.hero.backend.repositories.JobSkillRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

@Service
public class JobSkillService implements IServices<JobSkill, JobSkillId> {

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Override
    public JobSkill add(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }

    @Override
    public JobSkill update(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }

    @Override
    public JobSkill delete(JobSkillId jobSkillId) {
        return jobSkillRepository.findById(jobSkillId).map(jobSkill -> {
            jobSkillRepository.delete(jobSkill);
            return jobSkill;
        }).orElse(null);
    }

    @Override
    public Optional<JobSkill> findById(JobSkillId jobSkillId) {
        return Optional.of(jobSkillRepository.findById(jobSkillId)).orElse(null);
    }

    @Override
    public List<JobSkill> findAll() {
        return jobSkillRepository.findAll();
    }

    public List<String> findByJobId(Long jobId) {
        return jobSkillRepository.findSkillsByJobId(jobId);
    }
}
