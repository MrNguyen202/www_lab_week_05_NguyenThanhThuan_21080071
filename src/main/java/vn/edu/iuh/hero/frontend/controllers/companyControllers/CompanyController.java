/*
 * @ (#) CompanyController.java    1.0    12/12/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers.companyControllers;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/12/2024
 * @Version: 1.0
 *
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @RequestMapping("/viewProfile")
    public String viewProfile() {
        return "companies/company-profile";
    }

    @RequestMapping("/viewHomeCompany")
    public String viewHomeCompany() {
        return "companies/dashboardCompany";
    }
}
