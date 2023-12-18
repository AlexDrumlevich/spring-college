package telran.college.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.college.dto.*;
import telran.college.entities.Lecturer;
import telran.college.entities.Mark;
import telran.college.entities.Student;
import telran.college.entities.Subject;
import telran.college.repo.*;
import telran.exceptions.NotFoundException;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollegeServiceImpl implements CollegeService {
	final StudentRepo studentRepo;
	final LecturerRepo lecturerRepo;
	final SubjectRepo subjectRepo;
	final MarkRepo markRepo;

	@Override
	public Optional<Student> getStudentById(long id) {
		return studentRepo.findById(id);
	}

	@Override
	public Optional<Lecturer> getLecturerById(long id) {
		return lecturerRepo.findById(id);
	}
	
	@Override
	public Optional<Subject> getSubjectById(long id) {
		return subjectRepo.findById(id);
	}


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



	@Override
	@Transactional(readOnly = false)
	public PersonDto addStudent(PersonDto personDto) {
		if(studentRepo.existsById(personDto.id())) {
			throw new IllegalStateException(personDto.id() + " already exists");
		}
		return studentRepo.save(new Student(personDto)).build();
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto addLecturer(PersonDto personDto) {
		// TODO Auto-generated method stub
		if(lecturerRepo.existsById(personDto.id())) {
			throw new IllegalStateException(personDto.id() + " already exists"); 
		}
		return lecturerRepo.save(new Lecturer(personDto)).build();
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto addSubject(SubjectDto subjectDto) {
		Lecturer lecturer = null;
		if(subjectRepo.existsById(subjectDto.id())) {
			throw new IllegalStateException(subjectDto.id() + " already exists");
		}
		if(subjectDto.lecturerId() != null) {
			lecturer = lecturerRepo.findById(subjectDto.lecturerId())
					.orElseThrow(() -> new NotFoundException(subjectDto.lecturerId() + "not exists"));
		}
		Subject subject = new Subject(subjectDto);
		subject.setLecturer(lecturer);
		return subjectRepo.save(subject).build();
	}

	@Override
	@Transactional(readOnly = false)
	public MarkDto addMark(MarkDto markDto) {
		// TODO Auto-generated method stub
		Subject subject;
		Student student;
		StringBuilder exceptionMessageBuilder = new StringBuilder();
		exceptionMessageBuilder.append(studentRepo.existsById(markDto.studentId()) ? "" : "Student with the specified id not exist. ");
		exceptionMessageBuilder.append(subjectRepo.existsById(markDto.subjectId()) ? "" : "Subject with the specified id not exist. ");
		if(!exceptionMessageBuilder.isEmpty()) {
			throw new IllegalStateException(exceptionMessageBuilder.toString());
		}
		student = studentRepo.findById(markDto.studentId()).get();
		subject = subjectRepo.findById(markDto.subjectId()).get();

		return markRepo.save(new Mark(student, subject, markDto.score())).build();
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto updateStudent(PersonDto personDto) {
		Student student = studentRepo.findById(personDto.id())
				.orElseThrow(() -> new NotFoundException(personDto.id() + " not exists"));
		student.setCity(personDto.city());
		student.setPhone(personDto.phone());
		return student.build();
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto updateLecturer(PersonDto personDto) {
		// TODO Auto-generated method stub
		Lecturer lecturer = lecturerRepo.findById(personDto.id())
				.orElseThrow(() -> new NotFoundException(personDto.id() + " not exists"));
		lecturer.setCity(personDto.city());
		lecturer.setPhone(personDto.phone());
		return lecturer.build();
	}
	@Override
	@Transactional(readOnly = false)
	public PersonDto deleteLecturer(long id) {
		//find Lecturer by id (with possible NotFoundException)
		Lecturer lecturer = lecturerRepo.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("lecturer with %d  not exists", id)));

		//OnDelete - Set null

		//lecturerRepo.delete(lecturer)
		lecturerRepo.delete(lecturer);

		//returns lecturer.build();
		return lecturer.build();
	}


	@Override
	@Transactional(readOnly = false)
	public SubjectDto deleteSubject(long id) {
		//find Subject by id (with possible NotFoundException)
		Subject subject = subjectRepo.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("lecturer with %d  not exists", id)));
		
		// delete all marks with the subject - onDelete = Cascade
		
		//delete subject
		subjectRepo.delete(subject);
		
		//returns subject.build();
		return subject.build();
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> deleteStudentsHavingScoresLess(int nScores) {
		// TODO Auto-generated method stub
		
		List<Student> students = studentRepo.findStudentsHavingScoresLess(nScores);
		return students.stream().map(student -> {
			studentRepo.delete(student);
			return student.build();
		}).toList();
	
	}

}
