package telran.college.service;

import java.util.List;

import telran.college.dto.LecturerHours;
import telran.college.dto.MarksStudent;
import telran.college.dto.StudentMark;

public interface CollegeService {

	List<String> bestStudentsSubjectType(String type, int nStudents);

	List<StudentMark> studentsAvgMarks();
	List<StudentMark> studentsHaveMarkLessThen(int score);
	List<StudentMark> studentsByMonthNumber(int monthNumber);
	
	List<LecturerHours> mostLecturersWorkedHours(int nLecturers);
	
	List<MarksStudent> allMarksByStudentName(String name);
	
	List<LecturerHours> allLecturersByCity(String city);
	
}
