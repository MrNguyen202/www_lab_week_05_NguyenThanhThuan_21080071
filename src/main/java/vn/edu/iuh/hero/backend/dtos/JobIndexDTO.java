/*
 * @ (#) JobIndexDTO.java    1.0    12/9/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.dtos;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/9/2024
 * @Version: 1.0
 *
 */

import lombok.*;
import vn.edu.iuh.hero.backend.enums.SkillLevel;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.models.JobSkill;
import vn.edu.iuh.hero.backend.models.Skill;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobIndexDTO {
    private Long id;
    private String jobName;
    private String jobDesc;
    private String companyName;
    private List<String> skills;
    private SkillLevel skillLevel;

    public JobIndexDTO(Job job, List<String> skills) {
        this.id = job.getId();
        this.jobName = job.getJobName();
        this.jobDesc = job.getJobDesc();
        this.companyName = job.getCompany().getCompName();
        this.skills = skills;
    }
}
