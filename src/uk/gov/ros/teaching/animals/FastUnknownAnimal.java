package uk.gov.ros.teaching.animals;
//There is no public class in this .java compilation unit.
//Client code (outside this package) will not be aware that these animal types exist - this provides encapsulation at the
//package level

//Good practice to name the java file after the top level class - and mandatory if the top level class is public.

//As instances of this class will always have the 'unknown' type, we have added it to the class name.
class FastUnknownAnimal extends Animal{
	//static methods are not Polymorphic - this does not override the static displayInfo method in the parent - it hides it
	static void displayInfo(Animal a) {
		System.out.println("Static display by Fast Unknown animal of a " + a.getAnimalType());
	}
	
	//FastAnimal declares no constructor therefore it is given the default, public, empty constructor. This constructor
	//makes an implicit call to super() - therefore the parent class of FastAnimal must have an empty constructor.
	@Override
	public int getRequiredRoomTemperature() {
		return super.getRequiredRoomTemperature()-5;
	}
	
}
//There can be any number of top level non public classes in a .java file. However, normally there is only one.
//This class could be in its own .java file but is placed in this one due to its close relationship to its parent class
class LudicrousSpeedUnknownAnimal extends FastUnknownAnimal {
	@Override
	public int getRequiredRoomTemperature() {
		return super.getRequiredRoomTemperature()-10;
	}
}


