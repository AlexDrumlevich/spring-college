package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.LecturerHours;
import telran.college.dto.StudentMark;
import telran.college.entities.*;

public interface LecturerRepo extends JpaRepository<Lecturer, Long> {

	@Query(value = "Select l.name as name, sum(s.hours) as hours from students_lecturers l join subjects s on l.id = s.lecturer_id group by s.lecturer_id order by sum(s.hours) desc limit :nLecturers", nativeQuery=true)
	List<LecturerHours> findMostLecturersWorkedHours(int nLecturers);
	

	@Query(value = "SELECT name, phone FROM students_lecturers WHERE dtype = \'Lecturer\' AND city = :city", nativeQuery=true)
	List<LecturerHours> findAllLecturersByCity(String city);
}
