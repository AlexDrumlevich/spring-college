package telran.college.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.college.dto.LecturerHours;
import telran.college.dto.MarksStudent;
import telran.college.dto.StudentMark;
import telran.college.repo.LecturerRepo;
import telran.college.repo.MarkRepo;
import telran.college.repo.StudentRepo;
import telran.college.repo.SubjectRepo;

@Service
@RequiredArgsConstructor
public class CollegeServiceImplementation implements CollegeService {

	final StudentRepo studentRepo;
	final LecturerRepo lecturerRepo;
	final SubjectRepo subjectRepo;
	final MarkRepo markRepo;
	
	
	@Override
	public List<String> bestStudentsSubjectType(String type, int nStudents) {	
		return studentRepo.findBestStudentsSubjectType(type, nStudents);
	}
	
	@Override
	public List<StudentMark> studentsAvgMarks() {
		return studentRepo.studentsMarks();
	}

	@Override
	public List<LecturerHours> mostLecturersWorkedHours(int nLecturers) {
		return lecturerRepo.findMostLecturersWorkedHours(nLecturers);
	}

	@Override
	public List<StudentMark> studentsHaveMarkLessThen(int score) {
		return studentRepo.findStudentsHaveMarkLessThen(score);
	}

	@Override
	public List<StudentMark> studentsByMonthNumber(int monthNumber) {
		return studentRepo.findStudentsByMonthNumber(monthNumber);
	}

	@Override
	public List<MarksStudent> allMarksByStudentName(String name) {
		return markRepo.findAllMarksByStudentName(name);
	}

	@Override
	public List<LecturerHours> allLecturersByCity(String city) {
		return lecturerRepo.findAllLecturersByCity(city);
	}
}
