/*
 * @ (#) JobCompanyDTO.java    1.0    12/14/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.dtos;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/14/2024
 * @Version: 1.0
 *
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.models.JobSkill;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobCompanyDTO {
    private Job job;
    private List<JobSkill> jobSkills;

    public JobCompanyDTO(Job job, List<JobSkill> jobSkills) {
        this.job = job;
        this.jobSkills = jobSkills;
    }
}
