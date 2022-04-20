package dm2;

import java.util.Objects;

class Student implements Comparable<Student> {
	private String name;
	private Integer age;
	private Double score;
	
	Student(String name, int age, double score) {
		this.name = name;
		this.age = age;
		this.score = score;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(age, other.age) && Objects.equals(name, other.name) && Objects.equals(score, other.score);
	}

	@Override
	public int compareTo(Student o) {
		// To-do
		if (this.score == o.score) {
			if (this.age == o.age) {
				return this.name.compareTo(o.name);
			}
			else return this.age.compareTo(o.age);
		}
		else return this.score.compareTo(o.score);
	}
	
}
