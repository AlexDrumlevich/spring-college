package telran.college.entities;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import telran.college.dto.PersonDto;

@NoArgsConstructor
@Entity
public class Lecturer extends Person {
	  public Lecturer(PersonDto person) {
		  super(person);
	  }
}
