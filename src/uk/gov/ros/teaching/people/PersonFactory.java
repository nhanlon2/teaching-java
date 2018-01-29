package uk.gov.ros.teaching.people;

public class PersonFactory {
	public static final IPerson makePerson(String name, int age){
		return new Person(name, age);
	}
}
