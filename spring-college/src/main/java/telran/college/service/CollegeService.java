package telran.college.service;

import java.util.List;
import java.util.Optional;

import telran.college.dto.*;
import telran.college.entities.Lecturer;
import telran.college.entities.Student;
import telran.college.entities.Subject;

public interface CollegeService {
	Optional<Student> getStudentById(long id);
	Optional<Lecturer> getLecturerById(long id);
	Optional<Subject> getSubjectById(long id);
	
	List<String> bestStudentsSubjectType(String type, int nStudents);
	List<NameScore> studentsAvgMarks();
	List<LecturerHours> lecturersMostHours(int nLecturers);
	List<StudentCity> studentsScoresLess(int nThreshold);
	List<NamePhone> studentsBurnMonth(int month);
	List<NamePhone> lecturersCity(String city); 
	List<SubjectNameScore> subjectsScores(String studentName);
	
	PersonDto addStudent(PersonDto personDto);
	PersonDto addLecturer(PersonDto personDto);
	SubjectDto addSubject(SubjectDto subjectDto);
	MarkDto addMark(MarkDto markDto);
	PersonDto updateStudent(PersonDto personDto);
	PersonDto updateLecturer(PersonDto personDto);
	PersonDto deleteLecturer(long id);
	SubjectDto deleteSubject(long id);
	List<PersonDto> deleteStudentsHavingScoresLess(int nScores);
	
}
