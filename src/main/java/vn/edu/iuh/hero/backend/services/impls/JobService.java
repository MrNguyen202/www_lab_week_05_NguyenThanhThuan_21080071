/*
 * @ (#) JobService.java    1.0    11/13/2024
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
import vn.edu.iuh.hero.backend.dtos.JobCompanyDTO;
import vn.edu.iuh.hero.backend.dtos.JobIndexDTO;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.models.JobSkill;
import vn.edu.iuh.hero.backend.repositories.JobRepository;
import vn.edu.iuh.hero.backend.repositories.JobSkillRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobService implements IServices<Job, Long> {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Override
    public Job add(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job update(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job delete(Long aLong) {
        return jobRepository.findById(aLong).map(job -> {
            jobRepository.delete(job);
            return job;
        }).orElse(null);
    }

    @Override
    public Optional<Job> findById(Long aLong) {
        return Optional.of(jobRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Page<JobIndexDTO> getAllJobs(Pageable pageable) {
        Page<Job> jobs = jobRepository.findAllJobsWithCompany(pageable);

        // Chuyển đổi Job thành JobDTO
        return jobs.map(job -> {
            List<String> skills = jobSkillRepository.findSkillsByJobId(job.getId());
            return new JobIndexDTO(job, skills);
        });
    }

    public Page<JobIndexDTO> getJobsForCandidate(Long candidateId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Job> jobs = jobRepository.findJobsByCandidateId(candidateId, pageable);

        return jobs.map(job -> {
            List<String> skills = jobSkillRepository.findSkillsByJobId(job.getId());
            return new JobIndexDTO(job, skills);
        });
    }

    public Page<JobCompanyDTO> getJobsByCompany(Long companyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Job> jobs = jobRepository.findAllByCompany_Id(companyId, pageable);

        return jobs.map(job -> {
            List<JobSkill> jobSkills = jobSkillRepository.findAllByJob_Id(job.getId());
            return new JobCompanyDTO(job, jobSkills);
        });
    }
}
