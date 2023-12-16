package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.*;
import telran.college.entities.*;

public interface StudentRepo extends JpaRepository<Student, Long> {
	String JOIN_STUDENTS_MARKS = "FROM students_lecturers st join marks m on stid=st.id ";
	String JOIN_ALL = JOIN_STUDENTS_MARKS
			+ "join subjects sb on sb.id=suid ";
	/**********************/

	
	/*************************************/
	@Query("SELECT name as name, phone as phone from Student"
			+ " where EXTRACT (MONTH FROM birthDate) = :month")
	List<NamePhone> findStudentsBurnMonth(int month);

	/************************************************/
	
	@Query("SELECT s from Student s "
			+ "LEFT JOIN Mark m ON s.id = m.student.id GROUP BY s.id HAVING count(m.score) < :nScores")
	List<Student> findStudentsHavingScoresLess(int nScores);
}
