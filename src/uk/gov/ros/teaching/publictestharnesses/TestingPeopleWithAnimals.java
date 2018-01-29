package uk.gov.ros.teaching.publictestharnesses;

import uk.gov.ros.teaching.people.IPerson;
import uk.gov.ros.teaching.people.PersonFactory;
import uk.gov.ros.teaching.people.PersonWithConfidentialAge;

public class TestingPeopleWithAnimals {
	private static PersonFactory personFactory = new PersonFactory();
	
	public static void main (String [] args){
		
		IPerson p = personFactory.makePerson("Sue",2);
		System.out.println(p.toString());
		
		//########### Widening of scope in inherited overridden methods ########### 
		
		// create an anonymous class and break the intended encapsulation of PersonWithConfidentialAge
		PersonWithConfidentialAge ageRevealed = new PersonWithConfidentialAge("Dave",53) {
			@Override
			public int getAgeInConfidence() {//method is now public
				return super.getAgeInConfidence();
			};
		};
	}
}
