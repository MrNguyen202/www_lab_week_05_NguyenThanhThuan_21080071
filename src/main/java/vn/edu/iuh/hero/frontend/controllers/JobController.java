/*
 * @ (#) JobController.java    1.0    12/13/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/13/2024
 * @Version: 1.0
 *
 */

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.JobInsertDTO;
import vn.edu.iuh.hero.backend.enums.SkillLevel;
import vn.edu.iuh.hero.backend.enums.SkillType;
import vn.edu.iuh.hero.backend.ids.JobSkillId;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.models.JobSkill;
import vn.edu.iuh.hero.backend.models.Skill;
import vn.edu.iuh.hero.backend.services.impls.JobService;
import vn.edu.iuh.hero.backend.services.impls.JobSkillService;
import vn.edu.iuh.hero.backend.services.impls.SkillService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private JobSkillService jobSkillService;

    @Autowired
    private SkillService skillService;

    @PostMapping("/insert")
    public ModelAndView insertJob(
            @ModelAttribute JobInsertDTO jobInsertDTO,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        // Lấy thông tin công ty từ session
        Company company = (Company) session.getAttribute("user");
        Job job = new Job();
        job.setJobName(jobInsertDTO.getJobTitle());
        job.setJobDesc(jobInsertDTO.getJobDescription());
        job.setCompany(company);
        job.setExpiredDate(LocalDate.parse(jobInsertDTO.getExpiryDate()));
        jobService.add(job);

        // Lưu kỹ năng cho công việc
        for (Long skillId : jobInsertDTO.getSkillSelected()) {
            JobSkill jobSkill = new JobSkill();
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setJobId(job.getId());
            jobSkillId.setSkillId(skillId);

            jobSkill.setId(jobSkillId);
            jobSkill.setJob(job);
            jobSkill.setSkill(skillService.findById(skillId).get());
            jobSkill.setSkillLevel(SkillLevel.valueOf(jobInsertDTO.getSkillLevel().get(skillId)));
            jobSkillService.add(jobSkill);
        }
        modelAndView.addObject("message", "Job posted successfully!");
        modelAndView.addObject("skills", skillService.findAll());
        modelAndView.addObject("typeSkill", Arrays.asList(SkillType.values()));
        modelAndView.addObject("skillLevels", Arrays.asList(SkillLevel.values()));
        modelAndView.setViewName("/companies/post-job");
        return modelAndView;
    }
}
