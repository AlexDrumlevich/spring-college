package telran.college;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.college.dto.*;
import telran.college.entities.Lecturer;
import telran.college.entities.Person;
import telran.college.entities.Student;
import telran.college.entities.Subject;
import telran.college.service.CollegeService;
import telran.exceptions.NotFoundException;
@SpringBootTest
@Sql(scripts = {"db_test.sql"})
class CollegeServiceTest {
@Autowired
CollegeService collegeService;
	@Test
	void bestStudentsTypeTest() {
		List<String> students = collegeService.bestStudentsSubjectType("BACK_END", 2);
		String[] expected = {
				"David", "Yosef"
		};
		assertArrayEquals(expected, students.toArray(String[]::new));
	}
	@Test
	void studentsAvgScoreTest() {
		List<NameScore> studentMarks = collegeService.studentsAvgMarks();
		String [] students = {
			"David", "Rivka", "Vasya", "Sara", "Yosef"	
		};
		int [] scores = {
				96, 95, 83, 80, 78
		};
		NameScore[] studentMarksArr = studentMarks.toArray(NameScore[]::new);
		IntStream.range(0, students.length)
		.forEach(i -> {
			assertEquals(students[i], studentMarksArr[i].getName());
			assertEquals(scores[i], studentMarksArr[i].getScore());
		});
	}
	@Test
	void lecturersMostHoursTest() {
		List<LecturerHours> lecturersHours = collegeService.lecturersMostHours(2);
		LecturerHours[] lecturersHoursArr = lecturersHours.toArray(LecturerHours[]::new);
		String[] lecturers = {
				"Abraham", "Mozes"
		};
		int [] hours = {
			225, 130	
		};
		IntStream.range(0, hours.length).forEach(i -> {
			assertEquals(lecturers[i], lecturersHoursArr[i].getName());
			assertEquals(hours[i], lecturersHoursArr[i].getHours());
		});
	}
	@Test
	void studentsScoresLessTest() {
		List<StudentCity> studentCityList = collegeService.studentsScoresLess(1);
		assertEquals(1, studentCityList.size());
		StudentCity studentCity = studentCityList.get(0);
		assertEquals("Rehovot",studentCity.getCity());
		assertEquals("Yakob",studentCity.getName());
	}
	@Test
	void studentsBurnMonthTest() {
		String [] namesExpected = {
				"Vasya", "Yakob"
		};
		String [] phonesExpected = {
			"054-1234567", "051-6677889"	
		};
		NamePhone[] studentPhonesArr = collegeService.studentsBurnMonth(10)
				.toArray(NamePhone[]::new);

	
		assertEquals(phonesExpected.length, studentPhonesArr.length);
		IntStream.range(0,  phonesExpected.length).forEach(i -> {
			assertEquals(namesExpected[i], studentPhonesArr[i].getName());
			assertEquals(phonesExpected[i], studentPhonesArr[i].getPhone());
		});
	}
	@Test
	void lecturesCityTest() {
		String[]expectedNames = {
				"Abraham", "Mozes"
		};
		String[] expectedPhones = {
			"050-1111122", "054-3334567"	
		};
		//FIXME update test for jpql
		NamePhone[] namePhones = collegeService.lecturersCity("Jerusalem")
				.toArray(NamePhone[]::new);
		assertEquals(expectedNames.length, namePhones.length);
		IntStream.range(0, namePhones.length).forEach(i -> {
			assertEquals(expectedNames[i], namePhones[i].getName());
			assertEquals(expectedPhones[i], namePhones[i].getPhone());
		});
	}
	@Test
	void subjectsScoresTest() {
		String[] subjects = {
				"Java Core", "Java Technologies", "HTML/CSS", "JavaScript", "React"
		};
		int[] scores = {
				75, 60, 95, 85, 100
		};
		SubjectNameScore[] subjectScores = collegeService.subjectsScores("Vasya")
				.toArray(SubjectNameScore[]::new);
		assertEquals(scores.length, subjectScores.length);
		IntStream.range(0, scores.length).forEach(i -> {
			assertEquals(subjects[i], subjectScores[i].getSubjectName());
			assertEquals(scores[i], subjectScores[i].getScore());
		});
		
	}
	
	@Test
	void addStudentTest() {
		
		PersonDto newStudentDto = new PersonDto(1111, "Ivan", LocalDate.of(1999, 10, 20), "London", "0550055005");
		PersonDto existIdStudentDto = new PersonDto(123, "Ivan", LocalDate.of(1999, 10, 20), "London", "0550055005");
		
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addStudent(existIdStudentDto));
		collegeService.addStudent(newStudentDto);
		Optional<Student> studentOptional = collegeService.getStudentById(newStudentDto.id());
		assertDoesNotThrow(() -> studentOptional.get());
		Student student = studentOptional.get();
		assertTrue(student.getId() == newStudentDto.id());
		assertTrue(student.getBirthDate().isEqual(newStudentDto.birthDate()));
		assertTrue(student.getCity().equals(newStudentDto.city()));
		assertTrue(student.getName().equals(newStudentDto.name()));
		assertTrue(student.getPhone().equals(newStudentDto.phone()));
		
	}
	
	@Test
	void addLecturerTest() {
		
		PersonDto newLecturerDto = new PersonDto(1111, "Ivan", LocalDate.of(1999, 10, 20), "London", "0550055005");
		PersonDto existIdLecturerDto = new PersonDto(1230, "Ivan", LocalDate.of(1999, 10, 20), "London", "0550055005");
		
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addLecturer(existIdLecturerDto));
		collegeService.addLecturer(newLecturerDto);
		Optional<Lecturer> lecturerOptional = collegeService.getLecturerById(newLecturerDto.id());
		assertDoesNotThrow(() -> lecturerOptional.get());
		Lecturer lecturer = lecturerOptional.get();
		assertTrue(lecturer.getId() == newLecturerDto.id());
		assertTrue(lecturer.getBirthDate().isEqual(newLecturerDto.birthDate()));
		assertTrue(lecturer.getCity().equals(newLecturerDto.city()));
		assertTrue(lecturer.getName().equals(newLecturerDto.name()));
		assertTrue(lecturer.getPhone().equals(newLecturerDto.phone()));
		
	}

	@Test
	void addSubjectTest() {
		
		SubjectDto newSubjectDto = new SubjectDto(1111, "SQL", 10, null, SubjectType.BACK_END);
		SubjectDto existIdSubjectDto = new SubjectDto(323, "SQL", 10, null, SubjectType.BACK_END);
		
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addSubject(existIdSubjectDto));
		collegeService.addSubject(newSubjectDto);
		Optional<Subject> subjectOptional = collegeService.getSubjectById(newSubjectDto.id());
		assertDoesNotThrow(() -> subjectOptional.get());
		Subject subject = subjectOptional.get();
		assertTrue(subject.getId() == newSubjectDto.id());
		assertTrue(subject.getHours() == newSubjectDto.hours());
		assertTrue(subject.getName().equals(newSubjectDto.name()));
		assertTrue(subject.getType().equals(newSubjectDto.type()));

		
	}
	
	@Test
	void addMarkTest() {
		MarkDto markWithNotExistsSubject = new MarkDto(126, 10000, 100);
		MarkDto markWithNotExistsStudent = new MarkDto(10000, 126, 100);
		MarkDto marKCorrect = new MarkDto(126, 322, 100);
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addMark(markWithNotExistsSubject));
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addMark(markWithNotExistsStudent));
		
		List<SubjectNameScore> marksBefore = collegeService.subjectsScores("Rivka");
		MarkDto savedMarkDto = collegeService.addMark(marKCorrect);
		assertTrue(marKCorrect.subjectId() == savedMarkDto.subjectId());
		assertTrue(marKCorrect.studentId() == savedMarkDto.studentId());
		assertTrue(marKCorrect.score() == savedMarkDto.score());
		
		List<SubjectNameScore> marksAfter = collegeService.subjectsScores("Rivka");
		assertTrue(marksBefore.size() == (marksAfter.size() - 1));
		

	}
	@Test
	void updateStudentTest() {
		//(123, 'Vasya', 'Rehovot', '054-1234567', '2000-10-10', 'Student')
		Student studentBefore = collegeService.getStudentById(123).get();
		PersonDto studentDtoForUpdate = new PersonDto(123, "Petia", LocalDate.of(1998, 10, 11), "London", "0557654321");
		
		
		collegeService.updateStudent(studentDtoForUpdate);
		Student studentAfter = collegeService.getStudentById(123).get();
		
		assertTrue(studentBefore.getId() == studentAfter.getId());
		assertTrue(studentBefore.getName().equals(studentAfter.getName()));
		assertTrue(studentBefore.getBirthDate().isEqual(studentAfter.getBirthDate()));
		assertTrue(studentAfter.getPhone().equals(studentDtoForUpdate.phone()));
		assertTrue(studentAfter.getCity().equals(studentDtoForUpdate.city()));
		
		
		PersonDto studentNotExistsId = new PersonDto(123456, "Petia", LocalDate.of(1998, 10, 11), "London", "0557654321");
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateStudent(studentNotExistsId));
		
	}
	
	@Test
	void updateLecturerTest() {
		//(1230, 'Abraham',  'Jerusalem','050-1111122', '1957-01-23', 'Lecturer')
		Lecturer lecturerBefore = collegeService.getLecturerById(1230).get();
		PersonDto lecturerDtoForUpdate = new PersonDto(1230, "Petia", LocalDate.of(1998, 10, 11), "London", "0557654321");
		
		
		collegeService.updateLecturer(lecturerDtoForUpdate);
		Lecturer lecturerAfter = collegeService.getLecturerById(1230).get();
		
		
		assertTrue(lecturerBefore.getId() == lecturerAfter.getId());
		assertTrue(lecturerBefore.getName().equals(lecturerAfter.getName()));
		assertTrue(lecturerBefore.getBirthDate().isEqual(lecturerAfter.getBirthDate()));
		
		assertTrue(lecturerAfter.getPhone().equals(lecturerDtoForUpdate.phone()));
		assertTrue(lecturerAfter.getCity().equals(lecturerDtoForUpdate.city()));
		
		
		PersonDto lecturerNotExistsId = new PersonDto(123456, "Petia", LocalDate.of(1998, 10, 11), "London", "0557654321");
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateLecturer(lecturerNotExistsId));
		
	}
	
	@Test
	void deleteLecturerTest() {
		Lecturer lecturer = collegeService.getLecturerById(1230).get();
		PersonDto deletedLecturerDto = collegeService.deleteLecturer(1230);
		assertTrue(lecturer.getId() == deletedLecturerDto.id());
		assertTrue(lecturer.getBirthDate().isEqual(deletedLecturerDto.birthDate()));
		assertTrue(lecturer.getCity().equals(deletedLecturerDto.city()));
		assertTrue(lecturer.getName().equals(deletedLecturerDto.name()));
		assertTrue(lecturer.getPhone().equals(deletedLecturerDto.phone()));
		
		assertThrowsExactly(NoSuchElementException.class, () -> collegeService.getLecturerById(1230).get());
		
	}
	
	@Test
	void deleteSubjectTest() {
		Subject subject = collegeService.getSubjectById(321).get();
		
		SubjectDto deletedSubjectDto = collegeService.deleteSubject(321);
		assertTrue(subject.getId() == deletedSubjectDto.id());
		assertTrue(subject.getHours() == deletedSubjectDto.hours());
		assertTrue(subject.getLecturer().getId() == deletedSubjectDto.lecturerId());
		assertTrue(subject.getName().equals(deletedSubjectDto.name()));
		assertTrue(subject.getType().equals(deletedSubjectDto.type()));
		
		assertThrowsExactly(NoSuchElementException.class, () -> collegeService.getStudentById(321).get());
		
	}
	
	@Test
	void deleteStudentsHavingScoresLessTest() {
		List<StudentCity> studentsBeforeTill70 = collegeService.studentsScoresLess(70);
		List<StudentCity> allStudentsBefore = collegeService.studentsScoresLess(1000);
		collegeService.deleteStudentsHavingScoresLess(70);
		List<StudentCity> studentsAfterTill70 = collegeService.studentsScoresLess(70);
		List<StudentCity> allStudentsAfter = collegeService.studentsScoresLess(1000);
		
		assertTrue(!studentsBeforeTill70.isEmpty());
		assertTrue(studentsAfterTill70.isEmpty());
		assertTrue(allStudentsBefore.size() - studentsBeforeTill70.size() == allStudentsAfter.size());
		
	}
}
