package edu.bellevue.cis530.week4.service;

import edu.bellevue.cis530.week4.entity.Student;
import java.util.List;
import org.springframework.data.domain.Page;

public interface StudentService {
	Student createStudent(Student student);

	Student getStudentById(Long id);

	List<Student> getAllStudents();

	Student updateStudent(Long id, Student student);

	void deleteStudentById(Long id);

	List<Student> getByMajor(String major);

	List<Student> getStudentsByMajorJpql(String major);

	int deleteStudentsByEnrollmentYearJpql(Integer year);

	List<Student> getByGpaGreaterThan(Double gpa);

	List<Student> getByEnrollmentYearGreaterThan(Integer year);

	List<Student> getTop3ByGpa();

	List<Student> getAllSortedByLastName(String direction);

	Page<Student> getStudentsPaged(int page, int size);
}