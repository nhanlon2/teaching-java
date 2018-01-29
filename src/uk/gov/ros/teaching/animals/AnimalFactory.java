package uk.gov.ros.teaching.animals;
/*
 * Since none if the concrete classes that implement this interface are public, client code is forced to
 * depend on the Interface IAnimal. Public methods on the concrete Animal classes that are not exposed on
 * this interface will not be visible to any client code - allowing encapsulation.
 */
public class AnimalFactory {
	
	public static final IAnimal makeAnimal(String type){
		
		return new Animal(type);
	}
}

