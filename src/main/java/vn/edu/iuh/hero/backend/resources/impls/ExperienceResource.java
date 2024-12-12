/*
 * @ (#) ExperienceResource.java    1.0    11/17/2024
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
import vn.edu.iuh.hero.backend.models.Experience;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.ExperienceService;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceResource implements IResources<Experience, Long> {

    @Autowired
    private ExperienceService experienceService;

    @Override
    public ResponseEntity<Response> add(Experience experience) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(Long aLong, Experience experience) {
        return null;
    }

    @Override
    public ResponseEntity<Response> delete(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findById(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findAll() {
        return null;
    }
}
