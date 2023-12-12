package telran.college.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.college.dto.*;
import telran.college.repo.*;
@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {
	final StudentRepo studentRepo;
	final LecturerRepo lecturerRepo;
	final SubjectRepo subjectRepo;
	final MarkRepo markRepo;
	@Override
	public List<String> bestStudentsSubjectType(String type, int nStudents) {
		
		return markRepo.findBestStudentsSubjectType(SubjectType.valueOf(type), nStudents);
	}
	@Override
	public List<NameScore> studentsAvgMarks() {
		
		return markRepo.studentsMarks();
	}
	@Override
	public List<LecturerHours> lecturersMostHours(int nLecturers) {
		
		return subjectRepo.findLecturersMostHours(nLecturers);
	}
	@Override
	public List<StudentCity> studentsScoresLess(int nThreshold) {
		
		return markRepo.findStudentsScoresLess(nThreshold);
	}
	@Override
	public List<NamePhone> studentsBurnMonth(int month) {
		
		return studentRepo.findStudentsBurnMonth(month);
	}
	@Override
	public List<NamePhone> lecturersCity(String city) {
		return lecturerRepo.findByCity(city);
	}
	@Override
	public List<SubjectNameScore> subjectsScores(String studentName) {
		
		return markRepo.findByStudentName(studentName);
	}

}
