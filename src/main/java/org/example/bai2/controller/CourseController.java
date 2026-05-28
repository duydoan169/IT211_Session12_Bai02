package org.example.bai2.controller;

import org.example.bai2.model.Course;
import org.example.bai2.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        log.info("Request received - Method: getAllCourses, Endpoint: GET /api/courses");
        try {
            return ResponseEntity.ok(courseService.getAllCourses());
        } catch (RuntimeException ex) {
            log.error("Exception caught in getAllCourses: ", ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        log.info("Request received - Method: getCourseById, Endpoint: GET /api/courses/{}", id);
        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        } catch (RuntimeException ex) {
            log.error("Exception caught in getCourseById: ", ex);
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        log.info("Request received - Method: createCourse, Endpoint: POST /api/courses");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(course));
        } catch (RuntimeException ex) {
            log.error("Exception caught in createCourse: ", ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        log.info("Request received - Method: updateCourse, Endpoint: PUT /api/courses/{}", id);
        try {
            return ResponseEntity.ok(courseService.updateCourse(id, course));
        } catch (RuntimeException ex) {
            log.error("Exception caught in updateCourse: ", ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.info("Request received - Method: deleteCourse, Endpoint: DELETE /api/courses/{}", id);
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            log.error("Exception caught in deleteCourse: ", ex);
            throw ex;
        }
    }
}
