package edu.bellevue.cis530.week4.repository;

import edu.bellevue.cis530.week4.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByFirstName(String firstName);

	List<Student> findByLastName(String lastName);

	Optional<Student> findByEmail(String email);

	List<Student> findByMajorIgnoreCase(String major);

	List<Student> findByGpaGreaterThanEqual(Double minimumGpa);

	List<Student> findByGpaGreaterThan(Double gpa);

	List<Student> findByEnrollmentYear(Integer enrollmentYear);

	List<Student> findByEnrollmentYearGreaterThan(Integer year);

	List<Student> findTop3ByOrderByGpaDesc();

	@Query("SELECT s FROM Student s WHERE s.major = :major")
	List<Student> findStudentsByMajorJpql(@Param("major") String major);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query("DELETE FROM Student s WHERE s.enrollmentYear = :year")
	int deleteStudentsByEnrollmentYearJpql(@Param("year") Integer year);
}