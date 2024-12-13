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

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.CandidateTopLevelDTO;
import vn.edu.iuh.hero.backend.enums.SkillLevel;
import vn.edu.iuh.hero.backend.enums.SkillType;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;
import vn.edu.iuh.hero.backend.services.impls.SkillService;

import java.util.Arrays;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SkillService skillService;

    @RequestMapping("/viewProfile")
    public String viewProfile() {
        return "companies/company-profile";
    }

    @GetMapping("/viewPostJob")
    public ModelAndView viewPostJob() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("skills", skillService.findAll());
        modelAndView.addObject("typeSkill", Arrays.asList(SkillType.values()));
        modelAndView.addObject("skillLevels", Arrays.asList(SkillLevel.values()));
        modelAndView.setViewName("companies/post-job");
        return modelAndView;
    }

    @GetMapping("/viewHomeCompany")
    public ModelAndView viewHomeCompany(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            HttpSession session)
    {
        ModelAndView modelAndView = new ModelAndView();
        Page<CandidateTopLevelDTO> candidates = candidateService.getAllTopLevel(page, size);

        // Calculate start and end page numbers
        int pageStart = (page / 20) * 20;
        int pageEnd = Math.min(pageStart + 19, candidates.getTotalPages() - 1);

        modelAndView.addObject("cans", candidates.getContent());
        modelAndView.addObject("totalPages", candidates.getTotalPages());
        modelAndView.addObject("totalElements", candidates.getTotalElements());
        modelAndView.addObject("currentPage", candidates.getNumber());
        modelAndView.addObject("pageSize", candidates.getSize());
        modelAndView.addObject("pageStart", pageStart);
        modelAndView.addObject("pageEnd", pageEnd);
        modelAndView.setViewName("companies/dashboardCompany");
        return modelAndView;
    }
}
