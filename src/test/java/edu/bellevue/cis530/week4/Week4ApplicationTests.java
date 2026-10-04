package edu.bellevue.cis530.week4;

import static org.assertj.core.api.Assertions.assertThat;

import edu.bellevue.cis530.week4.entity.Student;
import edu.bellevue.cis530.week4.repository.StudentRepository;
import edu.bellevue.cis530.week4.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
class Week4ApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentService studentService;

	@Test
	void studentCrudEndpointsValidateAndPersistStudents() {
		assertThat(studentRepository.count()).isGreaterThanOrEqualTo(10);
		assertThat(studentRepository.findByFirstName("Ada")).isNotEmpty();
		assertThat(studentRepository.findByLastName("Lovelace")).isNotEmpty();
		assertThat(studentRepository.findByEmail("ada.lovelace@cis530.example.edu")).isPresent();
		assertThat(studentRepository.findByMajorIgnoreCase("computer science")).isNotEmpty();
		assertThat(studentRepository.findByGpaGreaterThanEqual(3.90)).isNotEmpty();
		assertThat(studentRepository.findByEnrollmentYear(2024)).isNotEmpty();
		assertThat(studentService.getByMajor("computer science")).isNotEmpty();
		assertThat(studentService.getStudentsByMajorJpql("Computer Science")).isNotEmpty();
		assertThat(studentService.getByGpaGreaterThan(3.9)).isNotEmpty();
		assertThat(studentService.getByEnrollmentYearGreaterThan(2023)).isNotEmpty();
		assertThat(studentService.getTop3ByGpa()).hasSize(3);
		assertThat(studentService.getAllSortedByLastName("ASC").stream().map(Student::getLastName).toList())
				.isSorted();
		assertThat(studentService.getStudentsPaged(0, 3).getTotalElements()).isGreaterThanOrEqualTo(10);
		assertThat(restTemplate.getForEntity("/api/students/major/{major}", Student[].class, "Computer Science")
				.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Student[]> byMajorJpql = restTemplate.getForEntity(
				"/api/students/major-jpql/{major}", Student[].class, "Computer Science");
		assertThat(byMajorJpql.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(byMajorJpql.getBody()).isNotEmpty();
		assertThat(restTemplate.getForEntity("/api/students/gpa/3.9", Student[].class)
				.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(restTemplate.getForEntity("/api/students/year/2023", Student[].class)
				.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(restTemplate.getForEntity("/api/students/top3", Student[].class)
				.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(restTemplate.getForEntity("/api/students/sort?direction=DESC", Student[].class)
				.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(restTemplate.getForEntity("/api/students/page?page=0&size=3", String.class)
				.getStatusCode()).isEqualTo(HttpStatus.OK);

		Student request = new Student("Ada", "Lovelace", "ada@example.com", "Computer Science", 3.95, 2024);
		ResponseEntity<Student> created = restTemplate.postForEntity("/api/students", request, Student.class);

		assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(created.getHeaders().getLocation()).isNotNull();
		assertThat(created.getBody()).isNotNull();
		assertThat(created.getBody().getMajor()).isEqualTo("Computer Science");
		assertThat(created.getBody().getGpa()).isEqualTo(3.95);
		assertThat(created.getBody().getEnrollmentYear()).isEqualTo(2024);
		Long studentId = created.getBody().getId();

		ResponseEntity<Student[]> all = restTemplate.getForEntity("/api/students", Student[].class);
		assertThat(all.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(all.getBody()).hasSizeGreaterThanOrEqualTo(11);

		Student updatedRequest = new Student("Augusta", "Lovelace", "augusta@example.com", "Mathematics", 3.99,
				2025);
		ResponseEntity<Student> updated = restTemplate.exchange("/api/students/" + studentId, HttpMethod.PUT,
				new HttpEntity<>(updatedRequest), Student.class);
		assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updated.getBody().getFirstName()).isEqualTo("Augusta");
		assertThat(updated.getBody().getMajor()).isEqualTo("Mathematics");
		assertThat(updated.getBody().getGpa()).isEqualTo(3.99);
		assertThat(updated.getBody().getEnrollmentYear()).isEqualTo(2025);

		ResponseEntity<String> deleted = restTemplate.exchange("/api/students/" + studentId, HttpMethod.DELETE, null,
				String.class);
		assertThat(deleted.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(deleted.getBody()).contains("Student deleted");

		ResponseEntity<String> missing = restTemplate.getForEntity("/api/students/" + studentId, String.class);
		assertThat(missing.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

		Student invalidRequest = new Student("", "Lovelace", "not-an-email");
		ResponseEntity<String> invalid = restTemplate.postForEntity("/api/students", invalidRequest, String.class);
		assertThat(invalid.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		ResponseEntity<String> deletedByYear = restTemplate.exchange("/api/students/year/2024/jpql", HttpMethod.DELETE,
				null, String.class);
		assertThat(deletedByYear.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(deletedByYear.getBody()).contains("\"deletedCount\":3");
		assertThat(studentRepository.findByEnrollmentYear(2024)).isEmpty();
	}

}
