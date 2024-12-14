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

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.dtos.CandidateForJobDTO;
import vn.edu.iuh.hero.backend.dtos.CandidateTopLevelDTO;
import vn.edu.iuh.hero.backend.dtos.JobCompanyDTO;
import vn.edu.iuh.hero.backend.enums.SkillLevel;
import vn.edu.iuh.hero.backend.enums.SkillType;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;
import vn.edu.iuh.hero.backend.services.impls.JobService;
import vn.edu.iuh.hero.backend.services.impls.SkillService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Optional;

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
    private JavaMailSender mailSender;

    @GetMapping("/viewProfile")
    public String viewProfile() {
        return "companies/company-profile";
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

    @PostMapping("/sendMail")
    public ModelAndView sendMailToCandidate(
            @RequestParam("emailTextarea") String emails,
            HttpSession session
    ) {
        ModelAndView modelAndView = new ModelAndView();

        // Inject JavaMailSender
        JavaMailSender mailSender;

        // Get company and job information from session/service
        Company company = (Company) session.getAttribute("user");
        Long jobId = (Long) session.getAttribute("jobId");
        System.out.println("Job ID: " + jobId);
        Job job = jobService.findById(jobId).orElse(null);

        if (job == null || company == null) {
            System.out.println("Job or company not found.");
            modelAndView.addObject("error", "Không tìm thấy công việc hoặc thông tin công ty.");
            modelAndView.setViewName("companies/candidate-for-job");
            return modelAndView;
        }

        // Split email string and validate
        String[] emailList = emails.split(",");
        for (String email : emailList) {
            if (!isValidEmail(email.trim())) {
                modelAndView.addObject("error", "Email không hợp lệ: " + email);
                modelAndView.setViewName("companies/candidate-for-job");
                return modelAndView;
            }
        }

        // Email content
        String subject = "Thông tin cơ hội việc làm tại " + company.getCompName();
        String bodyTemplate = """
        <p>Xin chào,</p>
        <p>Công ty <strong>%s</strong> xin gửi thông tin về cơ hội việc làm:</p>
        <p><strong>Tên công việc:</strong> %s</p>
        <p><strong>Mô tả công việc:</strong> %s</p>
        <p><strong>Địa chỉ làm việc:</strong> %s</p>
        <p><strong>Ngày hết hạn ứng tuyển:</strong> %s</p>
        <p>Chúng tôi mong chờ hồ sơ ứng tuyển từ bạn!</p>
        <p>Để biết thêm thông tin, vui lòng phản hồi về địa chỉ mail <e><u>%s</u></e> cho chúng tôi!</p>
        <p>Trân trọng,</p>
        <p><em>%s</em></p>
        """;

        String address = company.getAddress().getNumber() + " " + company.getAddress().getStreet() + ", " + company.getAddress().getCity() + ", " + company.getAddress().getCountry();
        String body = String.format(
                bodyTemplate,
                company.getCompName(),
                job.getJobName(),
                job.getJobDesc(),
                address,
                job.getExpiredDate().toString(),
                company.getEmailAddress(),
                company.getCompName()
        );

        try {
            // Send email to each address
            for (String email : emailList) {
                sendEmail(email.trim(), subject, body);
            }
            System.out.println("Mail sent successfully!");
            modelAndView.addObject("message", "Mail sent successfully!");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
            modelAndView.addObject("error", "Lỗi khi gửi email: " + e.getMessage());
        }

        modelAndView.setViewName("redirect:/company/viewMyJob");
        return modelAndView;
    }

    private void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        try {
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // true for HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }

    private boolean isValidEmail(String email) {
        // Basic email validation
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }


    @GetMapping("/viewCV/{candidateId}")
    public ResponseEntity<UrlResource> viewCV(@PathVariable Long candidateId) throws IOException {
        Optional<Candidate> candidate = candidateService.findById(candidateId);


        if (candidate.isEmpty() || candidate.get().getCv() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về lỗi nếu không tìm thấy file CV
        }

        // Lấy URL của file CV từ Cloudinary
        String cvFileUrl = candidate.get().getCv();

        // Tạo UrlResource từ URL của file Cloudinary
        URL url = new URL(cvFileUrl);
        UrlResource resource = new UrlResource(url);

        // Kiểm tra xem file có tồn tại và có thể đọc được không
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.status(404).body(null);
        }

        // Trả về file PDF với Content-Type là "application/pdf"
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)  // Đặt loại nội dung là PDF
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=cv.pdf")  // Đặt tên cho file
                .body(resource);
    }
}
