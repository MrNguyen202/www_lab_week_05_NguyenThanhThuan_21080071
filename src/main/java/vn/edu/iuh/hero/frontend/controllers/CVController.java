/*
 * @ (#) CVController.java    1.0    12/15/2024
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Controller
@RequestMapping("/cv")
public class CVController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/show/{candidateId}")
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
