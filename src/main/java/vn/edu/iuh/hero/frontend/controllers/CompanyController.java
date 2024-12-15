/*
 * @ (#) CompanyController.java    1.0    12/12/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/12/2024
 * @Version: 1.0
 *
 */

import com.neovisionaries.i18n.CountryCode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.CandidateForJobDTO;
import vn.edu.iuh.hero.backend.dtos.CandidateTopLevelDTO;
import vn.edu.iuh.hero.backend.dtos.JobCompanyDTO;
import vn.edu.iuh.hero.backend.enums.SkillLevel;
import vn.edu.iuh.hero.backend.enums.SkillType;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.services.impls.*;
import java.util.Arrays;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private JobService jobService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/viewProfile")
    public String viewProfile() {
        return "companies/company-profile";
    }

    @GetMapping("/viewSearchCV")
    public ModelAndView viewSearchCV(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("companies/search-company");
        return modelAndView;
    }

    @GetMapping("/viewMyJob")
    public ModelAndView viewMyJob(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            HttpSession session
    ) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = (Company) session.getAttribute("user");
        Page<JobCompanyDTO> jobs = jobService.getJobsByCompany(company.getId(), page, size);

        // Calculate start and end page numbers
        int pageStart = (page / 9) * 9;
        int pageEnd = Math.min(pageStart + 8, jobs.getTotalPages() - 1);

        modelAndView.addObject("jobs", jobs.getContent());
        modelAndView.addObject("totalPages", jobs.getTotalPages());
        modelAndView.addObject("totalElements", jobs.getTotalElements());
        modelAndView.addObject("currentPage", jobs.getNumber());
        modelAndView.addObject("pageSize", jobs.getSize());
        modelAndView.addObject("pageStart", pageStart);
        modelAndView.addObject("pageEnd", pageEnd);

        modelAndView.setViewName("companies/my-job");
        return modelAndView;
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

    @GetMapping("/findCandidates/{jobId}")
    public ModelAndView findCandidatesForJob(
            @PathVariable Long jobId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            HttpSession session
            ) {
        ModelAndView modelAndView = new ModelAndView();
        Page<CandidateForJobDTO> candidates = candidateService.findCandidatesByJobId(jobId, page, size);
        session.setAttribute("jobId", jobId);

        // Calculate start and end page numbers
        int pageStart = (page / 10) * 10;
        int pageEnd = Math.min(pageStart + 9, candidates.getTotalPages() - 1);

        modelAndView.addObject("candidates", candidates.getContent());
        modelAndView.addObject("job", jobService.findById(jobId).get());

        modelAndView.addObject("totalPages", candidates.getTotalPages());
        modelAndView.addObject("totalElements", candidates.getTotalElements());
        modelAndView.addObject("currentPage", candidates.getNumber());
        modelAndView.addObject("pageSize", candidates.getSize());
        modelAndView.addObject("pageStart", pageStart);
        modelAndView.addObject("pageEnd", pageEnd);

        modelAndView.setViewName("companies/candidate-for-job");
        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public ModelAndView updateProfile(
            @RequestParam("name") String compName,
            @RequestParam("emailAddress") String email,
            @RequestParam("phone") String phone,
            @RequestParam("url") String url,
            @RequestParam("address") String address,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = (Company) session.getAttribute("user");
        company.setCompName(compName);
        company.setEmailAddress(email);
        company.setPhone(phone);
        company.getAddress().setStreet(address.split(", ")[1]);
        company.getAddress().setCity(address.split(", ")[2]);
        company.getAddress().setCountry(CountryCode.valueOf(address.split(", ")[3]));
        company.getAddress().setNumber(address.split(", ")[0]);
        company.getAddress().setZipcode(address.split(", ")[4]);
        addressService.update(company.getAddress());
        companyService.update(company);
        modelAndView.addObject("user", company);
        modelAndView.setViewName("companies/company-profile");
        return modelAndView;
    }
}
