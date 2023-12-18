package telran.college.entities;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import telran.college.dto.PersonDto;

@Entity
@NoArgsConstructor
public class Student extends Person {
	public Student(PersonDto personDto) {
		super(personDto);
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", city=" + city + ", phone=" + phone
				+ "]";
	}
}
