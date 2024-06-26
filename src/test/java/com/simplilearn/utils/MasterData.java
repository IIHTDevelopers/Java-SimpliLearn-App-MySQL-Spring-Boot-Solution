package com.simplilearn.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simplilearn.dto.CourseDTO;
import com.simplilearn.dto.StudentDTO;

public class MasterData {

	public static CourseDTO getCourseDTO() {
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setId(1L);
		courseDTO.setTitle("Introduction to Java");
		courseDTO.setDescription("A comprehensive course covering Java basics to advanced topics");
		// Add student IDs to the course if needed
		courseDTO.getStudentIds().addAll(Arrays.asList(1L, 2L));
		return courseDTO;
	}

	public static List<CourseDTO> getCourseDTOList() {
		List<CourseDTO> courseDTOList = new ArrayList<>();
		CourseDTO courseDTO = getCourseDTO(); // Reusing the getCourseDTO method
		courseDTOList.add(courseDTO);
		return courseDTOList;
	}

	public static StudentDTO getStudentDTO() {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setId(1L);
		studentDTO.setName("Jane Doe");
		studentDTO.setEmail("jane.doe@example.com");
		// Add course IDs to the student if needed
		studentDTO.getCourseIds().addAll(Arrays.asList(1L, 2L));
		return studentDTO;
	}

	public static List<StudentDTO> getStudentDTOList() {
		List<StudentDTO> studentDTOList = new ArrayList<>();
		StudentDTO studentDTO = getStudentDTO(); // Reusing the getStudentDTO method
		studentDTOList.add(studentDTO);
		return studentDTOList;
	}

	public static String asJsonString(Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule()); // Register the module to handle Java 8 date/time types
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
