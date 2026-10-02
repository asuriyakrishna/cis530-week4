package edu.bellevue.cis530.week4.service.impl;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.exception.ResourceNotFoundException;
import edu.bellevue.cis530.week4.repository.StudentRepository;
import edu.bellevue.cis530.week4.service.StudentService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public Student createStudent(Student student) {
		Student newStudent = new Student(student.getFirstName(), student.getLastName(), student.getEmail(),
				student.getMajor(), student.getGpa(), student.getEnrollmentYear());
		return studentRepository.save(newStudent);
	}

	@Override
	@Transactional(readOnly = true)
	public Student getStudentById(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student updateStudent(Long id, Student student) {
		Student existingStudent = getStudentById(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setMajor(student.getMajor());
		existingStudent.setGpa(student.getGpa());
		existingStudent.setEnrollmentYear(student.getEnrollmentYear());
		return studentRepository.save(existingStudent);
	}

	@Override
	public void deleteStudentById(Long id) {
		studentRepository.delete(getStudentById(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getByMajor(String major) {
		return studentRepository.findByMajorIgnoreCase(major);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getStudentsByMajorJpql(String major) {
		return studentRepository.findStudentsByMajorJpql(major);
	}

	@Override
	public int deleteStudentsByEnrollmentYearJpql(Integer year) {
		return studentRepository.deleteStudentsByEnrollmentYearJpql(year);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getByGpaGreaterThan(Double gpa) {
		return studentRepository.findByGpaGreaterThan(BigDecimal.valueOf(gpa));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getByEnrollmentYearGreaterThan(Integer year) {
		return studentRepository.findByEnrollmentYearGreaterThan(year);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getTop3ByGpa() {
		return studentRepository.findTop3ByOrderByGpaDesc();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Student> getAllSortedByLastName(String direction) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		return studentRepository.findAll(Sort.by(sortDirection, "lastName"));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Student> getStudentsPaged(int page, int size) {
		return studentRepository.findAll(PageRequest.of(page, size));
	}
}