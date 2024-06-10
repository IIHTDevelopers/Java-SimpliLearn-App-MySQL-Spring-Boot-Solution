package com.simplilearn.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplilearn.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	/**
	 * Finds courses by their title containing the specified string. This method
	 * could be useful for implementing search functionality.
	 * 
	 * @param title the title or part of the title to search for
	 * @return a list of courses matching the search criteria
	 */
	List<Course> findByTitleContainingIgnoreCase(String title);
}
