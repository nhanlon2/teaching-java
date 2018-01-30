package uk.gov.ros.teaching.animals;

import java.io.File;
//Animal is not public - client code outside the package cannot see this type
class Animal implements IAnimal {
	//static fields come first, public before private
	public static final String UNKNOWN_TYPE = "unknown";
	private static final int TEMPERATURE_DIFFERENCE = 14;
	private static final int BODY_TEMPERATURE = 37;
	
	//###########Initialisation blocks ###########
	//this is a static block - it will run once when the class is loaded
	static {
		try {
			//create a file in a static block - allowing us to handle exceptions which a simple
			//variable initialisation could not do.
			File f = new File("blah");
			f.createNewFile();
		} catch (Exception e){
			// it is illegal to throw any exception here
			//throw e;
		}
	}
	//instance fields come before constructor, public before private
	private String animalType = UNKNOWN_TYPE;
	private int costToFeed;
	
	//###########Initialisation blocks ###########
	// this is an instance block, it will run every time an object is created, before the constructor(s) run
	{
		//do stuff - also illegal to throw exceptions here
		//throw new IllegalArgumentException();
	}
	
	//public constructor comes first
	public Animal(String type) {
		this();// call empty constructor
		if (type == null || type.isEmpty()) {// protecting the class invariant -
			// all Animals must have a type.
			throw new IllegalArgumentException("animal must have a type");
		}
		this.animalType = type;
	}
	
	Animal() {// default scoped - client code outside the package will not be
				// aware this constructor exists

		// We are allowing instances of animal with the 'unknown' type to be
		// created in this package. Code outside the
		// package will not see this constructor and therefore can only create
		// instances of Animals with a known type.
		// If having animal instances with the 'unknown' type would an error in
		// this package, we should remove the 'unknown' type as the default
		// value and instead make the Animal class abstract.

		// Constructor code should never call a public method. Observe what
		// happens when the DangerousOverrideAnimal
		// child class overrides the getRequiredRoomTemperature method
		this.costToFeed = (getRequiredRoomTemperature() / 2) * 100;
	}
	
	static void displayInfo(Animal a) {
		System.out.println("Static display of Generic animal " + a.getAnimalType());
	}
	
	//instance methods come after constructor, group them logically

	public String getAnimalType() {
		return animalType;
	}

	@Override
	public int getRequiredRoomTemperature() {
		return BODY_TEMPERATURE - TEMPERATURE_DIFFERENCE;
	}

	public int getCostToFeed() {
		return costToFeed;
	}
	//overloaded printInfo methods
	void printInfo(Animal a) {
		System.out.println("Generic animal " + a.getAnimalType());
	}

	void printInfo(FastUnknownAnimal a) {
		System.out.println("Fast Animal " + a.getCostToFeed());
	}
	
}
