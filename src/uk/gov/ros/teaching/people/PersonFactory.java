package uk.gov.ros.teaching.people;

//########### Using factories instead of new ###########
public class PersonFactory {
	public static final IPerson makePerson(String name, int age){
		return new Person(name, age);
	}
}
