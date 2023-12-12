package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.LecturerHours;
import telran.college.entities.*;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
	
	/******************************************************************/
	@Query("SELECT lecturer.name as name, sum(hours) as hours from Subject "
		+ "group by lecturer.id order by sum(hours) desc limit :nLecturers")
	List<LecturerHours> findLecturersMostHours(int nLecturers);
}
