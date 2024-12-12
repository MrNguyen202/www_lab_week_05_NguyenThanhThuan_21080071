/*
 * @ (#) ProfileController.java    1.0    12/12/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers.candidateControllers;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.JobIndexDTO;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.User;
import vn.edu.iuh.hero.backend.services.impls.AddressService;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;
import vn.edu.iuh.hero.backend.services.impls.JobService;
import vn.edu.iuh.hero.backend.services.impls.UserService;

import java.time.LocalDate;

@Controller
@RequestMapping("/candidate")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private JobService jobService;

    @GetMapping("/viewProfile")
    public String viewProfile() {
        return "candidates/candidate-profile";
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
        System.out.println(address);
        candidate.getAddress().setStreet(address.split(", ")[1]);
        candidate.getAddress().setCity(address.split(", ")[2]);
        candidate.getAddress().setCountry(CountryCode.valueOf(address.split(", ")[3]));
        candidate.getAddress().setNumber(address.split(", ")[0]);
        candidate.getAddress().setZipcode(address.split(", ")[4]);
        addressService.update(candidate.getAddress());
        candidateService.update(candidate);
        modelAndView.addObject("user", candidate);
        modelAndView.setViewName("candidates/candidate-profile");
        return modelAndView;
    }
}
