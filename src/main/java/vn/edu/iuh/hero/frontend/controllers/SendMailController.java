/*
 * @ (#) SendMailController.java    1.0    12/15/2024
 *
 *
 */

package vn.edu.iuh.hero.frontend.controllers;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/15/2024
 * @Version: 1.0
 *
 */

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.services.impls.JobService;

@Controller
@RequestMapping("/mail")
public class SendMailController {

    @Autowired
    private JobService jobService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/sendMails")
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
}
