package uk.gov.ros.teaching.animals;

public class AnimalTestHarness {

	public static void main (String [] args){
		
		//######### Constructor chaining #########
		
		FastUnknownAnimal fa  = new FastUnknownAnimal(); //empty constructor is implicit when a class provides no constructors of its own
		//The implicit empty constructor has the visibility of its class - if FastAnimal were a public class, client
		//code outside the package could construct Animal's with 'unknown' type - breaking intended encapsulation
		
		//Mysterious animal declares its own empty constructor. 
		//This makes an implicit call to the parent class' empty constructor
		//Constructors which take parameters do not make implicit calls to their parent class' paramaterised constructor .
		//(See SlowAnimal, below).
		MysteriousAnimal ma = new MysteriousAnimal();
		System.out.println("Mysterious animal has a cost to feed of: "+ma.getCostToFeed());
		
		//FastAnimal faTypes  = new FastAnimal("Antelope"); //constructors are not inherited so this wont compile
		
		//SlowAnimal sa = new SlowAnimal(); SlowAnimal has provided a constructor - 
		//therefore has lost its implicit empty constructor - so the commented code wont compile
		SlowAnimal saTyped = new SlowAnimal("sloth");
		System.out.println("Slow animal's type is: "+ saTyped.getAnimalType());// prints unknown....why? Can you fix?
		System.out.println("Slow Animals cost to feed is: "+saTyped.getCostToFeed());
		//costToFeed has been initialised correctly for the SlowAnimal class. All constructors that do not
		//make an explicit call to another constructor, make an implicit call
		//to their parent class' empty constructor.
		
		AmazingAnimal aa = new AmazingAnimal("Amazing");
		System.out.println("Amazing animal's type is: "+ aa.getAnimalType());// prints unknown....why? Can you fix?
		AnimalWithCorrectPolymorphism awcp = new AnimalWithCorrectPolymorphism("done correctly");
		System.out.println("Polymorphic animal's type is: "+ awcp.getAnimalType());
		
		//######### Constructors should never call public methods #########
		
		//DangerousOverrideAnimal doa = new DangerousOverrideAnimal("bit of a mess now");
		//This will give a null pointer if run - the Animal constructor is invoked by the DangerousOverrideAnimal
		//constructor. In turn the Animal constructor calls 'getRequiredRoomTemperature' which is overriden by
		//DangerousOverrideAnimal. Unfortunately the overriden method in DangerousOverrideAnimal refers to an instance
		//variable which will still be null at this point - it is only given a value when the DangerousOverrideAnimal
		//constructor runs - which wont be until after the Animal constructor has finished running. You can uncomment the
		//code above and run in debug mode to see this in action.
		
		//######### Overriding #########
		
		//Method overriding happens at runtime - regardless of the declared type of the object reference.
		//The JVM has a dispatch table that is dynamically updated at runtime containing real types mapped to declared types
		Animal a = new Animal();
		System.out.println("Animal has a cost to feed of:" +a.getCostToFeed());
		System.out.println("Fast Unknown animal has a cost to feed of:"+fa.getCostToFeed());
		Animal another = (Animal)fa;
		
		System.out.println("Another animal has a cost to feed of: "+another.getCostToFeed());
		
		//######### Overloading #########
		
		//Method overloading happens at compile time, not at runtime
		//The declared types of method parameters affect the choice of overloaded method, not their real types.
		a.printInfo(a);
		a.printInfo(fa);
		a.printInfo(another);
		
		//######### STATIC METHODS #########
		
		//Static methods are not polymorphic - invocation depends on the declared type of the object reference and there is
		//no overriding of static methods. Note that (although not good practice) static methods can be invoked via object instances as well as through
		//the class type.
		fa.displayInfo(fa);
		((Animal)fa).displayInfo(fa);//invokes the displayInfo of the Animal class, not the FastAnimal.
		
	}
}
