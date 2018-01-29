package uk.gov.ros.teaching.people;

import uk.gov.ros.teaching.animals.AnimalFactory;
import uk.gov.ros.teaching.animals.IAnimal;

public class PeopleTestHarness {
	private static AnimalFactory animalFactory = new AnimalFactory();
	
	public static void main (String [] args){
		//########### Code to interfaces not to concrete classes ########### 
		IPerson p = new Person("Sue",2);
		System.out.println(p.toString());
		IAnimal fish = animalFactory.makeAnimal("fish");
		
		//########### Anonymous classes ########### 
		IPerson testPerson = new IPerson() {
			
			@Override
			public String getName() { // you have to provide implementations for all non-default methods on the interface
				return "anonymous...";
			}
			
			@Override
			public int getAge() {
				return 0;
			}
		};
		
		System.out.println("Anonymous class "+testPerson.toString());// NOT the same result as an instance of Person
		
		//########### Copy Constructor ###########
		//We only want to expose IPerson to client code therefore we use the copy constructor of Person
		// to achieve this.
		PersonWithConfidentialAge discretePerson = new PersonWithConfidentialAge("Dave", 53);
		IPerson indiscretePerson = new Person(discretePerson);
		System.out.println(indiscretePerson.getAge());
	}
}
