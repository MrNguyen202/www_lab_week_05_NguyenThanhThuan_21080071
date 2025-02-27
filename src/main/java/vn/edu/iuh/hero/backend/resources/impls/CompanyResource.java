/*
 * @ (#) CompanyResource.java    1.0    11/17/2024
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyResource implements IResources<Company, Long> {

    @Autowired
    private CompanyService companyService;

    @Override
    public ResponseEntity<Response> add(Company company) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(Long aLong, Company company) {
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
