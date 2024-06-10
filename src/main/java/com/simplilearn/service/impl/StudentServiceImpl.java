package com.simplilearn.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplilearn.dto.CourseDTO;
import com.simplilearn.dto.StudentDTO;
import com.simplilearn.entity.Course;
import com.simplilearn.entity.Student;
import com.simplilearn.exception.ResourceNotFoundException;
import com.simplilearn.repo.CourseRepository;
import com.simplilearn.repo.StudentRepository;
import com.simplilearn.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ModelMapper modelMapper) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public StudentDTO enrollStudentInCourse(Long studentId, Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		student.getCourses().add(course);
		Student savedStudent = studentRepository.save(student);

		return modelMapper.map(savedStudent, StudentDTO.class);
	}

	@Override
	public List<CourseDTO> viewStudentEnrollments(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		return student.getCourses().stream().map(course -> modelMapper.map(course, CourseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public StudentDTO manageStudentEnrollment(Long studentId, Long courseId, boolean enroll) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		if (enroll) {
			student.getCourses().add(course);
		} else {
			student.getCourses().remove(course);
		}

		Student updatedStudent = studentRepository.save(student);
		return modelMapper.map(updatedStudent, StudentDTO.class);
	}

	@Override
	public List<StudentDTO> listStudentsForCourse(Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found"));

		return course.getStudents().stream().map(student -> modelMapper.map(student, StudentDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public StudentDTO createStudent(StudentDTO studentDTO) {
		Student student = modelMapper.map(studentDTO, Student.class);
		Student savedStudent = studentRepository.save(student);
		return modelMapper.map(savedStudent, StudentDTO.class);
	}
}
