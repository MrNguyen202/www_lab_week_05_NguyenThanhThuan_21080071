/*
 * @ (#) JobSkillResource.java    1.0    11/17/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.resources.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/17/2024
 * @Version: 1.0
 *
 */

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.hero.backend.ids.JobSkillId;
import vn.edu.iuh.hero.backend.models.JobSkill;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.JobSkillService;

@RestController
@RequestMapping("/api/job-skills")
public class JobSkillResource implements IResources<JobSkill, JobSkillId> {

    @Autowired
    private JobSkillService jobSkillService;

    @Override
    public ResponseEntity<Response> add(JobSkill jobSkill) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(JobSkillId jobSkillId, JobSkill jobSkill) {
        return null;
    }

    @Override
    public ResponseEntity<Response> delete(JobSkillId jobSkillId) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findById(JobSkillId jobSkillId) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findAll() {
        return null;
    }

    public ResponseEntity<Response> findByJobId(Long jobId) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Find job skill by job id successfully",
                jobSkillService.findByJobId(jobId)
        ));
    }
}
