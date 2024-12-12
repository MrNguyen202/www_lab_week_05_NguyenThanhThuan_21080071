/*
 * @ (#) HomeController.java    1.0    11/17/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.JobIndexDTO;
import vn.edu.iuh.hero.backend.services.impls.JobService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private JobService jobService;

    @GetMapping({"/login", "/logout"})
    public String directToLogin() {
        return "auth/login";
    }

    @GetMapping({"/", "/home"})
    public ModelAndView showJob(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        ModelAndView modelAndView = new ModelAndView("index");
        Pageable pageable = PageRequest.of(page, size);
        Page<JobIndexDTO> jobIndexDTOPage = jobService.getAllJobs(pageable);
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
        return modelAndView;
    }

    @GetMapping("/viewSearch")
    public String viewSearch() {
        return "searchIndex";
    }

}
