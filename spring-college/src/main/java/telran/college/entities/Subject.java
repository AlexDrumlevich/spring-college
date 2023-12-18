package telran.college.entities;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.college.dto.SubjectDto;
import telran.college.dto.SubjectType;
@Entity
@Table(name="subjects")
@Getter
@NoArgsConstructor
public class Subject {
	@Id
	long id;
	@Column(nullable = false)
	String name;
	int hours;
	@ManyToOne
	@JoinColumn(name="lecturer_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	Lecturer lecturer;
	@Enumerated(value=EnumType.STRING)
	@Column(nullable = false)
	SubjectType type;
	
	public Subject(SubjectDto subjectDto) {
		id = subjectDto.id();
		name = subjectDto.name();
		hours = subjectDto.hours();
		type = subjectDto.type();
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public SubjectDto build() {
		return new SubjectDto(id, name, hours, lecturer == null ? null : lecturer.id, type);
	}
	
	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", hours=" + hours + ", lecturer=" + lecturer.name + ", type=" + type
				+ "]";
	}
	
	
	

}
