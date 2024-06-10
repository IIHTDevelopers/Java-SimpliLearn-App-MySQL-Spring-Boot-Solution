package com.simplilearn.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.entity.Course;
import com.simplilearn.exception.ResourceNotFoundException;
import com.simplilearn.repo.CourseRepository;
import com.simplilearn.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CourseDTO createCourse(CourseDTO courseDTO) {
		Course course = modelMapper.map(courseDTO, Course.class);
		Course savedCourse = courseRepository.save(course);
		return modelMapper.map(savedCourse, CourseDTO.class);
	}

	@Override
	public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		course.setTitle(courseDTO.getTitle());
		course.setDescription(courseDTO.getDescription());
		Course updatedCourse = courseRepository.save(course);
		return modelMapper.map(updatedCourse, CourseDTO.class);
	}

	@Override
	public boolean deleteCourse(Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		courseRepository.delete(course);
		return true;
	}

	@Override
	public List<CourseDTO> listAllCourses() {
		List<Course> courses = courseRepository.findAll();
		return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public CourseDTO getCourse(Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));
		return modelMapper.map(course, CourseDTO.class);
	}
}
