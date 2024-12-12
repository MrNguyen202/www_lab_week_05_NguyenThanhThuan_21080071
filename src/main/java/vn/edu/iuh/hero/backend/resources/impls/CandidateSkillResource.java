/*
 * @ (#) CandidateSkillResource.java    1.0    11/17/2024
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.hero.backend.ids.CandidateSkillId;
import vn.edu.iuh.hero.backend.models.CandidateSkill;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;

@RestController
@RequestMapping("/api/candidate-skills")
public class CandidateSkillResource implements IResources<CandidateSkill, CandidateSkillId> {

    @Autowired
    private CandidateService candidateService;

    @Override
    public ResponseEntity<Response> add(CandidateSkill candidateSkill) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(CandidateSkillId candidateSkillId, CandidateSkill candidateSkill) {
        return null;
    }

    @Override
    public ResponseEntity<Response> delete(CandidateSkillId candidateSkillId) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findById(CandidateSkillId candidateSkillId) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findAll() {
        return null;
    }
}
