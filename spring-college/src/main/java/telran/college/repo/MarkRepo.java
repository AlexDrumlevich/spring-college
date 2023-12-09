package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.MarksStudent;
import telran.college.entities.*;

public interface MarkRepo extends JpaRepository<Mark, Long> {

	
	@Query(value = "SELECT sb.name as subject, score FROM (SELECT s.id, m.suid, s.name, score FROM students_lecturers s JOIN marks m on s.id = m.stid where name = :name) st_mr JOIN subjects sb on st_mr.suid = sb.id", nativeQuery=true)
	List<MarksStudent> findAllMarksByStudentName(String name);
	
}