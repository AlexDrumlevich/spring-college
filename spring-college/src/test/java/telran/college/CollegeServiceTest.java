package telran.college;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.college.dto.LecturerHours;
import telran.college.dto.MarksStudent;
import telran.college.dto.StudentMark;
import telran.college.service.CollegeService;

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
		Map<String, Integer> avgScores = new LinkedHashMap<>();
		avgScores.put("David", 96);
		avgScores.put("Rivka", 95);
		avgScores.put("Vasya", 83);
		avgScores.put("Sara", 80);
		avgScores.put("Yosef", 78);
		Iterator<Entry<String, Integer>> avgScoresIterator = avgScores.entrySet().iterator();
		List<StudentMark> studentMarks = collegeService.studentsAvgMarks();
		
		studentMarks.forEach(sm -> {
			
			assertTrue(avgScoresIterator.hasNext());
			Entry<String, Integer> nextEntry = avgScoresIterator.next();
			assertEquals(nextEntry.getKey(), sm.getName());
			assertEquals(nextEntry.getValue(), sm.getScore());
		});
		assertEquals(studentMarks.size(), avgScores.entrySet().size());
	}
	
	@Test
	void studentsHaveMarkLessThen() {
		Map<String, String> expectedMapResultsLess80 = new HashMap<>();
		expectedMapResultsLess80.put("Vasya", "Rehovot");
		expectedMapResultsLess80.put("Sara", "Beersheva");
		expectedMapResultsLess80.put("Yosef", "Rehovot");

		List<StudentMark> studentsMarksLess80 = collegeService.studentsHaveMarkLessThen(80);
		
		studentsMarksLess80.forEach(sm -> {			
			assertEquals(expectedMapResultsLess80.get(sm.getName()), sm.getCity());
		});
		assertEquals(studentsMarksLess80.size(), expectedMapResultsLess80.entrySet().size());
	}
	
	@Test
	void findMostLecturersWorkedHoursTest() {
		Map<String, Integer> hoursAmount = new LinkedHashMap<>();
		hoursAmount.put("Abraham", 225);
		hoursAmount.put("Mozes", 130);
		hoursAmount.put("Sockratus", 65);

		Iterator<Entry<String, Integer>> hoursAmountIterator = hoursAmount.entrySet().iterator();
		List<LecturerHours> lecturersHours = collegeService.mostLecturersWorkedHours(2);
		
		lecturersHours.forEach(lh -> {
			
			assertTrue(hoursAmountIterator.hasNext());
			Entry<String, Integer> nexEntry = hoursAmountIterator.next();
			assertEquals(nexEntry.getKey(), lh.getName());
			assertEquals(nexEntry.getValue(), lh.getHours());
		});
		assertEquals(lecturersHours.size(), 2);
			
	}
	
	@Test
	void findStudentsByMonthNumberTest() {
		Map<String, String> expectedMapResultsMonth10 = new HashMap<>();
		expectedMapResultsMonth10.put("Vasya", "054-1234567");
		expectedMapResultsMonth10.put("Yakob", "051-6677889");
		
		List<StudentMark> studentsMonth10 = collegeService.studentsByMonthNumber(10);
		
		studentsMonth10.forEach(sm -> {			
			assertEquals(expectedMapResultsMonth10.get(sm.getName()), sm.getPhone());
		});
		assertEquals(studentsMonth10.size(), expectedMapResultsMonth10.entrySet().size());
	}
	
	@Test
	void findAllMarksByStudentNameTest() {
		
		Map<String, Integer> studentScoresMapResultByRivka = new HashMap<>();
		studentScoresMapResultByRivka.put("HTML/CSS", 100);
		studentScoresMapResultByRivka.put("JavaScript", 90);
		
		List<MarksStudent> marksByRivka = collegeService.allMarksByStudentName("Rivka");
		
		marksByRivka.forEach(marks -> {			
			assertEquals(studentScoresMapResultByRivka.get(marks.getSubject()), marks.getScore());
		});
		assertEquals(marksByRivka.size(), studentScoresMapResultByRivka.entrySet().size());
	}
	
	@Test
	void findAllLecturersByCityTest() {
		Map<String, String> expectedMapResultsByJerusalem = new HashMap<>();
		expectedMapResultsByJerusalem.put("Abraham", "050-1111122");
		expectedMapResultsByJerusalem.put("Mozes", "054-3334567");
		
		List<LecturerHours> lecturersByJerusalem = collegeService.allLecturersByCity("Jerusalem");
		
		lecturersByJerusalem.forEach(l -> {			
			assertEquals(expectedMapResultsByJerusalem.get(l.getName()), l.getPhone());
		});
		assertEquals(lecturersByJerusalem.size(), expectedMapResultsByJerusalem.entrySet().size());
	}
}
