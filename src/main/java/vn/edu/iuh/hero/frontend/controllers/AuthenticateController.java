/*
 * @ (#) AuthenticateController.java    1.0    12/9/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/9/2024
 * @Version: 1.0
 *
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.CandidateTopLevelDTO;
import vn.edu.iuh.hero.backend.dtos.JobIndexDTO;
import vn.edu.iuh.hero.backend.enums.Role;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.models.User;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;
import vn.edu.iuh.hero.backend.services.impls.CompanyService;
import vn.edu.iuh.hero.backend.services.impls.JobService;
import vn.edu.iuh.hero.backend.services.impls.UserService;

import java.util.Random;

@Controller
@RequestMapping("/auth")
public class AuthenticateController {

    @Autowired
    private UserService userService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobService jobService;

    @GetMapping("/viewLogin")
    public String viewLogin() {
        return "auth/login";
    }

    @GetMapping("/viewRegister")
    public String viewRegister() {
        return "auth/sign-up";
    }

    //Register
    @PostMapping("/register")
    public ModelAndView register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm-password") String confirmPassword,
            @RequestParam("role") String role
    ) {
        ModelAndView modelAndView = new ModelAndView();
        Random random = new Random();
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Kiểm tra email đã tồn tại
        if (userService.findByEmail(email).isPresent()) {
            modelAndView.addObject("error", "Email is already in use!");
            modelAndView.setViewName("auth/sign-up");
            return modelAndView;
        }

        // Kiểm tra password và confirm password
        if (!password.equals(confirmPassword)) {
            modelAndView.addObject("error", "Password and Confirm Password are not the same!");
            modelAndView.setViewName("auth/sign-up");
            return modelAndView;
        }

        // Lưu thông tin tùy thuộc vào role
        if (role.equalsIgnoreCase("candidate")) {
            Candidate candidate = new Candidate();
            candidate.setEmail(email);
            candidate.setPassword(password);
            candidate.setAvatar("https://res.cloudinary.com/dlf3hmpnl/image/upload/v1734196770/kjhygf6cqjhu7hev7s9a.jpg");
            candidate.setRole(Role.CANDIDATE);
            candidate.setFullName(random.ints(8, 0, CHARACTERS.length())
                    .mapToObj(CHARACTERS::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString());
            candidateService.add(candidate);
            modelAndView.addObject("message", "Register successfully!");
        } else if (role.equalsIgnoreCase("company")) {
            Company company = new Company();
            company.setEmail(email);
            company.setPassword(password);
            company.setRole(Role.COMPANY);
            company.setCompName(random.ints(8, 0, CHARACTERS.length())
                    .mapToObj(CHARACTERS::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString());
            companyService.add(company);
            modelAndView.addObject("message", "Register successfully!");
        }
        modelAndView.setViewName("auth/sign-up");
        return modelAndView;
    }


    //Login
    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session
    ) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.findByEmail(email).isPresent()) {
            User user = userService.findByEmail(email).get();
//            System.out.println(user);
            if (user.getPassword().equals(password)) {
                modelAndView.addObject("message", "Login successfully!");

                // Kiểm tra loại đối tượng (Candidate, Company, etc.)
                if (user instanceof Candidate) {
                    Candidate candidate = (Candidate) user; // Ép kiểu sang Candidate
                    session.setAttribute("user", candidate);
//                    System.out.println(candidate);
                } else if (user instanceof Company) {
                    Company company = (Company) user; // Ép kiểu sang Company
                    session.setAttribute("user", company);
                }

                // Điều hướng tới trang dashboard phù hợp
                if (user.getRole().equals(Role.COMPANY)) {
                    Page<CandidateTopLevelDTO> candidates = candidateService.getAllTopLevel(0, 20);

                    // Calculate start and end page numbers
                    int pageStart = (0 / 20) * 20;
                    int pageEnd = Math.min(pageStart + 19, candidates.getTotalPages() - 1);

                    modelAndView.addObject("cans", candidates.getContent());
                    modelAndView.addObject("totalPages", candidates.getTotalPages());
                    modelAndView.addObject("totalElements", candidates.getTotalElements());
                    modelAndView.addObject("currentPage", candidates.getNumber());
                    modelAndView.addObject("pageSize", candidates.getSize());
                    modelAndView.addObject("pageStart", pageStart);
                    modelAndView.addObject("pageEnd", pageEnd);
                    modelAndView.setViewName("companies/dashboardCompany");
                } else {
                    Candidate candidate = (Candidate) session.getAttribute("user");
                    Page<JobIndexDTO> jobIndexDTOPage = jobService.getJobsForCandidate(candidate.getId(), 0, 20);

                    if(candidate != null && jobIndexDTOPage.isEmpty()) {
                        jobIndexDTOPage = jobService.getJobsByExpiredDate(0, 20);
                    }
                    // Calculate start and end page numbers
                    int pageStart = (0 / 20) * 20;
                    int pageEnd = Math.min(pageStart + 19, jobIndexDTOPage.getTotalPages() - 1);

                    modelAndView.addObject("jobs", jobIndexDTOPage.getContent());
                    modelAndView.addObject("totalPages", jobIndexDTOPage.getTotalPages());
                    modelAndView.addObject("totalElements", jobIndexDTOPage.getTotalElements());
                    modelAndView.addObject("currentPage", jobIndexDTOPage.getNumber());
                    modelAndView.addObject("pageSize", jobIndexDTOPage.getSize());
                    modelAndView.addObject("pageStart", pageStart);
                    modelAndView.addObject("pageEnd", pageEnd);
                    modelAndView.setViewName("candidates/dashboardCandidate");
                }

                return modelAndView;
            } else {
                modelAndView.addObject("error", "Login failed!");
                modelAndView.setViewName("auth/login");
                return modelAndView;
            }
        } else {
            modelAndView.addObject("warning", "Email is not exist!");
            modelAndView.setViewName("auth/login");
            return modelAndView;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

}
