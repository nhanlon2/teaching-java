package uk.gov.ros.teaching.publictestharnesses;

import uk.gov.ros.teaching.people.IPerson;
import uk.gov.ros.teaching.people.Person;
import uk.gov.ros.teaching.people.PersonFactory;

public class TestingPeopleWithAnimals {
	private static PersonFactory personFactory = new PersonFactory();
	
	public static void main (String [] args){
		
		IPerson p = personFactory.makePerson("Sue",22);
		System.out.println(p.toString());
		
		
		// create an anonymous class and break the intended encapsulation of PersonWithConfidentialAge
		Person daveAgeRevealed = new Person("Dave",53) {
			@Override
			public int getAge() {
				return super.getAgeInConfidence();
			};
		};
		Person daveAgeModifiedAsIntended = new Person("Dave",53);
		//just a hack here  - this advances the 'createdOn' date value in the person class. In production code we would need to use something
		//more accurate that a Date comparison.
		sleep();
		//########### Copy Constructor ###########
		Person daveCopied = new Person(daveAgeModifiedAsIntended);
		Person anotherDaveAge53 = new Person("Dave",53);
		
		System.out.println("Daves age in confidence is: " + daveAgeModifiedAsIntended.getAge());
		System.out.println("Daves age revealed is: " + daveAgeRevealed.getAge());
		
		//########### overloading matching the most specific matching method signature ###########
		//overloading occurs at compile time
		Util.print((IPerson)daveAgeModifiedAsIntended);
		Util.print(daveAgeModifiedAsIntended);
		
		//########### Overriding Equals() and Hashcode()  ###########
		System.out.println("Dave age revealed and modified represent the same thing: "+daveAgeModifiedAsIntended.equals(daveAgeRevealed));
		//these two objects should be equals() regardless of when created as they use the copy constructor
		System.out.println("Dave age revealed and copied represent the same thing: "+daveAgeModifiedAsIntended.equals(daveCopied));
		//but these two have a differnt date value
		System.out.println("Dave age revealed and another represent the same thing: "+daveAgeModifiedAsIntended.equals(anotherDaveAge53));
	}

	private static void sleep() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {

		}

	}
	
}
