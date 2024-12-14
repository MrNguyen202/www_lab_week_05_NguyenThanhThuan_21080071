/*
 * @ (#) CandidateController.java    1.0    12/12/2024
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
import vn.edu.iuh.hero.backend.dtos.JobIndexDTO;
import vn.edu.iuh.hero.backend.models.Address;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.services.impls.AddressService;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;
import vn.edu.iuh.hero.backend.services.impls.JobService;
import vn.edu.iuh.hero.backend.services.impls.UserService;

import java.time.LocalDate;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private UserService userService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private JobService jobService;

    @GetMapping("/viewProfile")
    public ModelAndView viewProfile(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = (Candidate) session.getAttribute("user");
//        System.out.println(candidate);
        modelAndView.addObject("user", candidate);
        modelAndView.setViewName("candidates/candidate-profile");
        return modelAndView;
    }

    @GetMapping("/viewHomeCandidate")
    public ModelAndView viewHomeCandidate(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            HttpSession session)
    {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = (Candidate) session.getAttribute("user");
        Page<JobIndexDTO> jobIndexDTOPage = jobService.getJobsForCandidate(candidate.getId(), page, size);
        if(candidate != null && jobIndexDTOPage.isEmpty()) {
            jobIndexDTOPage = jobService.getJobsByExpiredDate(page, size);
        }
        // Calculate start and end page numbers
        int pageStart = (page / 20) * 20;
        int pageEnd = Math.min(pageStart + 19, jobIndexDTOPage.getTotalPages() - 1);

        modelAndView.addObject("jobs", jobIndexDTOPage.getContent());
        modelAndView.addObject("totalPages", jobIndexDTOPage.getTotalPages());
        modelAndView.addObject("totalElements", jobIndexDTOPage.getTotalElements());
        modelAndView.addObject("currentPage", jobIndexDTOPage.getNumber());
        modelAndView.addObject("pageSize", jobIndexDTOPage.getSize());
        modelAndView.addObject("pageStart", pageStart);
        modelAndView.addObject("pageEnd", pageEnd);
        modelAndView.setViewName("candidates/dashboardCandidate");
        return modelAndView;
    }

    @PostMapping("/updateProfile")
    public ModelAndView updateProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam("emailAddress") String email,
            @RequestParam("phone") String phone,
            @RequestParam("dob") LocalDate dob,
            @RequestParam("address") String address,
            HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Candidate candidate = (Candidate) session.getAttribute("user");
        candidate.setFullName(fullName);
        candidate.setEmailAddress(email);
        candidate.setPhone(phone);
        candidate.setDob(dob);

        Address address1 = new Address();
        address1.setStreet(address.split(", ")[1]);
        address1.setCity(address.split(", ")[2]);
        address1.setCountry(CountryCode.valueOf(address.split(", ")[3]));
        address1.setNumber(address.split(", ")[0]);
        address1.setZipcode(address.split(", ")[4]);

        if(candidate.getAddress() == null) {
            addressService.add(address1);
            candidate.setAddress(address1);
        }else {
            candidate.getAddress().setStreet(address.split(", ")[1]);
            candidate.getAddress().setCity(address.split(", ")[2]);
            candidate.getAddress().setCountry(CountryCode.valueOf(address.split(", ")[3]));
            candidate.getAddress().setNumber(address.split(", ")[0]);
            candidate.getAddress().setZipcode(address.split(", ")[4]);
            addressService.update(candidate.getAddress());
        }
        candidateService.update(candidate);
        modelAndView.addObject("user", candidate);
        modelAndView.setViewName("candidates/candidate-profile");
        return modelAndView;
    }

    @GetMapping("/viewSearch")
    public ModelAndView viewSearch() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("candidates/search-candidate");
        return modelAndView;
    }

    @GetMapping("/viewCV")
    public ModelAndView viewCV() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("candidates/cv-candidate");
        return modelAndView;
    }

}
