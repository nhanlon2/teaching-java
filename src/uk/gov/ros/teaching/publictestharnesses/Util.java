package uk.gov.ros.teaching.publictestharnesses;

import uk.gov.ros.teaching.people.IPerson;
import uk.gov.ros.teaching.people.Person;

/*
 * You will often see code in classes called 'Util' or 'Helper' or something similar.
 * Try to avoid creating classes like this - they become a dumping ground for code. Classes should really correspond to
 * something in the domain being modelled by the code. In our domain here of People and Animals, what is a 'Util'??
 */
public class Util {
	
	// create a named class and break the intended encapsulation of PersonWithConfidentialAge
	private static class PersonAgeRevealer extends Person {
		//this class has to implement at least one constructor that calls a Person constructor
		public PersonAgeRevealer(Person anOther) {
			super(anOther);
		}
		//########### Widening of scope in inherited overridden methods ########### 
		@Override
		public int getAgeInConfidence() { //inherited method has had access widened
			return super.getAgeInConfidence();
		}
		
	}
	//when all of a classes methods are static it is another sign that it is just a convenient dumping ground for code
	static void print(Person p){
		System.out.println(p.getName() + " has revealed age of: " +new PersonAgeRevealer(p).getAgeInConfidence());
	}
	
	static void print(IPerson p) {
		System.out.println(p.getName() + " has confidential age of: " +p.getAge());
	}
}
