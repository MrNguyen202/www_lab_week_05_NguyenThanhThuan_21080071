/*
 * @ (#) JobResource.java    1.0    11/17/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.resources.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/17/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.hero.backend.dtos.JobIndexDTO;
import vn.edu.iuh.hero.backend.models.Job;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobResource implements IResources<Job, Long> {

    @Autowired
    private JobService jobService;

    @Override
    public ResponseEntity<Response> add(Job job) {
        return null;
    }

    @Override
    public ResponseEntity<Response> update(Long aLong, Job job) {
        return null;
    }

    @Override
    public ResponseEntity<Response> delete(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<Response> findById(Long aLong) {
        return null;
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<Response> findAll() {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all jobs successfully",
                jobService.findAll()
        ));
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Response> getAllJobs(@PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5); // Mỗi trang 10 công việc
        Page<JobIndexDTO> jobs = jobService.getAllJobs(pageable);
        return ResponseEntity.ok(new Response(
                200,
                "Jobs fetched successfully",
                jobs
        ));
    }

    @GetMapping("/recommendations/{candidateId}")
    public ResponseEntity<Response> getRecommendedJobs(
            @PathVariable Long candidateId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<JobIndexDTO> jobs = jobService.getJobsForCandidate(candidateId, page, size);
        return ResponseEntity.ok(new Response(
                200,
                "Get recommended jobs successfully",
                jobs
        ));
    }

}
