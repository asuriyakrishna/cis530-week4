package edu.bellevue.cis530.week4.controller;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.service.StudentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@GetMapping("/by-major")
	public List<Student> getByMajor(@RequestParam String major) {
		return studentService.getByMajor(major);
	}

	@GetMapping("/major-jpql/{major}")
	public List<Student> getStudentsByMajorJpql(@PathVariable String major) {
		return studentService.getStudentsByMajorJpql(major);
	}

	@DeleteMapping("/year/{year}/jpql")
	public ResponseEntity<Map<String, Integer>> deleteStudentsByEnrollmentYearJpql(@PathVariable Integer year) {
		int deletedCount = studentService.deleteStudentsByEnrollmentYearJpql(year);
		return ResponseEntity.ok(Map.of("year", year, "deletedCount", deletedCount));
	}

	@GetMapping("/by-gpa")
	public List<Student> getByGpaGreaterThan(@RequestParam Double gpa) {
		return studentService.getByGpaGreaterThan(gpa);
	}

	@GetMapping("/by-enrollment-year")
	public List<Student> getByEnrollmentYearGreaterThan(@RequestParam Integer year) {
		return studentService.getByEnrollmentYearGreaterThan(year);
	}

	@GetMapping("/top-gpa")
	public List<Student> getTop3ByGpa() {
		return studentService.getTop3ByGpa();
	}

	@GetMapping("/sorted")
	public List<Student> getAllSortedByLastName(@RequestParam(defaultValue = "ASC") String direction) {
		return studentService.getAllSortedByLastName(direction);
	}

	@GetMapping("/page")
	public Page<Student> getStudentsPaged(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return studentService.getStudentsPaged(page, size);
	}

	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable Long id) {
		return studentService.getStudentById(id);
	}

	@PostMapping
	public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
		Student createdStudent = studentService.createStudent(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdStudent.getId()).toUri();
		return ResponseEntity.created(location).body(createdStudent);
	}

	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
		return studentService.updateStudent(id, student);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return ResponseEntity.ok(Map.of("message", "Student deleted"));
	}
}