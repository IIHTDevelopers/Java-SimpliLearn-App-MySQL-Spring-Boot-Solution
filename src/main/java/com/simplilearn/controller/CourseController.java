package com.simplilearn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService courseService;

	@Autowired
	private Environment env;

	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@PostMapping
	public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
		CourseDTO createdCourse = courseService.createCourse(courseDTO);
		return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
	}

	@PutMapping("/{courseId}")
	public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId,
			@Valid @RequestBody CourseDTO courseDTO) {
		CourseDTO updatedCourse = courseService.updateCourse(courseId, courseDTO);
		return ResponseEntity.ok(updatedCourse);
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<CourseDTO> getCourse(@PathVariable Long courseId) {
		CourseDTO course = courseService.getCourse(courseId);
		return ResponseEntity.ok(course);
	}

	@DeleteMapping("/{courseId}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
		courseService.deleteCourse(courseId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<CourseDTO>> listAllCourses() {
		List<CourseDTO> courses = courseService.listAllCourses();
		return ResponseEntity.ok(courses);
	}

	@GetMapping("/profile")
	public ResponseEntity<String> getProfile() {
		String profileData = env.getProperty("profile.validate.data", "This is default profile");
		return new ResponseEntity<>(profileData, HttpStatus.OK);
	}
}
