package org.example.bai2.service;

import org.example.bai2.exception.CourseNotFoundException;
import org.example.bai2.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    private final List<Course> courses = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Course getCourseById(Long id) {
        return courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Course not found with ID: {}", id);
                    return new CourseNotFoundException("Course not found with ID: " + id);
                });
    }

    public Course createCourse(Course course) {
        if (course.getCourseName() == null || course.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        course.setId(idGenerator.getAndIncrement());
        courses.add(course);
        log.info("Successfully created course: {}", course.getCourseName());
        return course;
    }

    public Course updateCourse(Long id, Course courseData) {
        if (courseData.getCourseName() == null || courseData.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        Course existingCourse = getCourseById(id);
        existingCourse.setCourseName(courseData.getCourseName());
        existingCourse.setInstructor(courseData.getInstructor());
        existingCourse.setDurationHours(courseData.getDurationHours());
        existingCourse.setFee(courseData.getFee());
        log.info("Successfully updated course with ID: {}", id);
        return existingCourse;
    }

    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courses.remove(course);
    }
}
