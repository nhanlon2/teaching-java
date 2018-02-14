#Object Oriented Design: OOD

Classes, Objects, Interfaces. Accessibility - private, default, protected, public. Abstract classes.  Immutability. Constructors, initialisation blocks, static blocks. Protecting class invariants using constructors instead of getters/setters. Polymorphism, overloading and overriding. Extending an abstract class. Inherited methods. Widening of accessibility for inherited methods. Always favour composition over Inheritance. Final methods and classes. Factory methods. Overloaded constructors using ‘this’, constructor should never call a public method, calling constructor of parent class with super(), explicit call to super() with empty constructor. Inner classes (static and non static). Overriding equals and hashcode. Casting and instance of bad - visitor pattern as alternative. Chain of command?

##SOLID
Classes should be designed with SOLID principles in mind. See a simple description here:
https://en.wikipedia.org/wiki/SOLID_(object-oriented_design)

Using Java, code should always depend on Interfaces ( **Dependency Inversion**). 
Classes should be kept small ( **Single Object Responsibility**). 
Using TDD can help with the task of designing small classes that are easy to test - large classes, 
tested after the fact are invariably very cumbersome to test. Keep interfaces small and ‘domain specific’. 
For example, a class representing a Person who is an Employee and a Purchaser should implement IPerson, 
IEmployee and IPurchaser, not some all consuming Interface with methods for all three. This is **Interface Segregation**.
 Classes that are not final should be designed such that their inheritable methods will be valid in child classes - 
 this is **Liskov Substitution**. 
 Implementing the **open/closed principle** is more of an emergent property of using good OOD than any specific piece of advice.

##Structure of a compilation unit
By convention, declaration of members in a .java compilation unit should follow a specific order :
1) Package statement 2) Class and interface declaration 3) static variables 4)  instance variables 5) constructors and 6) methods. Public variables come first then protected, default and lastly private. Methods should be grouped in a logical way that enhances readability rather than grouped by accessibility. If this is not followed, code quality tools such as sonar will complain and other developers will be very confused, especially when this happens in large classes.
See: https://web.archive.org/web/20130516014426/http://www.oracle.com/technetwork/java/codeconventions-141855.html#1852

##Initialisation blocks
These can be static and non static. Static blocks load once per classloader, non static blocks load once per object initialisation, before any constructors run.

##Instantiation of an object
When an object is created, the order of instantiation is: static members and blocks, instance members and blocks, then the constructor.

Constructor chaining and inheritance
Objects that have parents need to construct the parent object prior to their own construction. This is done by constructor chaining. Constructors can call the constructor of their immediate superclass by using ‘super’ as the first line of code in the constructor. All constructors that do not make an explicit call to super, make an implict call to super() (the parent class’ empty constructor). Constructors should never call a public/protected/default scoped method. This is because these methods can be overriden by child classes. If this happens, code execution will jump to the child class - while the parent class’ constructor may not have finished setting up class invariants etc.



##Inheritance - 
What is inherited? Inherited code is visible on the local object’s instance of ‘this’. Private members are not inherited nor are static members. Public and protected members are inherited, default scoped members are inherited if the child class is in the same package as the parent. Fields with the same name as parent fields hide the parent fields. Static members do the same. Static methods with the same signature as a parent static method hide the parent method (they do NOT override it - only inherited methods can be overriden). Static methods with the same name but a different signature to a parent method can overload that method. However static methods are not polymorphic - there are only visible to client code based on the declared type of a reference variable.

##Using final
**Final classes cannot be extended and final methods cannot be overridden. Final fields are immutable once assigned a value and after all of a classes constructors have finished execution.**

##Overloading and overriding

**Always use the @Overrides annotation to get compile time checking that ensures you are overriding a method correctly, especially with equals().** Overriding involves a child class implementing the a method with the same name and signature as method it has inherited in a parent class. (Therefore private, final or static methods may not be overridden). The overriding method may have wider access than the parent method and it may throw fewer exceptions than the parent method. Overriding occurs at runtime, regardless of the declared type of an object reference the JVM dispatches method calls to the actual type. 

In contract overloading occurs at compile time. When two methods have the same name but different signature (different method parameters) then the compiler will bind calls to that method name based on the declared types of the arguments passed to the method.
When there is any overlap between method signatures, **the compiler links to the most specific matching method signature**. The most specific type for a parameter is the one furthest down the inheritance chain. For example  - given a Person class that implements an IPerson interface and given a method signature in a Util class,  ‘print(IPerson p)’ and ‘print(Person p)’. See the Util code for an example.

Widening of scope in inherited overridden methods.
When declaring methods as protected scoped (which is usually done in order to make the method accessible to unit tests while not being public) be aware of the fact that any child class will inherit such methods and can widen the accessibility to public - breaking any intended encapsulation. If it is really necessary to change accessibiity of methods for testing purposes then use default scope.

##Inner classes
Inner classes can be non-static or static. Static inner classes are easier to understand, as they do not need an enclosing instance of 
their outer class in order to be instantiated - a static inner class is mostly a form of namespacing. 
**The type of an inner class is OuterClassName.InnerClassName.** 

A new instance of a non static inner class needs an instance of its outer class to be created - eg it is created using the unique syntax of: 
 	
 	outerObject.new InnerClass(); 


A new instance of a static inner class is created using standard syntax: 

	new OuterClassName.InnerClassName();

Non static inner classes have access to all members (including private) of their outer class; static inner classes can only access static members of the outer class. Non static inner classes cannot have static members. Fields in the outer class having the same name in the inner class are ‘shadowed’ - the only way to access them from the inner class is through using ‘OuterClassName.this.field’.

##Accessibility of classes
Top level classes can only be public or default scoped. Inner classes may have any scope and may also be static. 

##Anonymous classes 
A class can be declared and instantiated inline, making it an anonymous class. Typically this comes about when using new to make an instance of an interface or abstract class. This was often done in test code before the advent of Mocking frameworks such as Mockito.

##Overriding Equals() and Hashcode()

**Always override both at the same time - or neither. All fields used to calculate equals must also be used to calculate hashcode.** 
**When two objects are equals() their hashcode() must match.** 
**Hash collisions between objects that are not equals() are allowed but must be infrequent.**

The default equals() (inherited from the Object class) uses ‘==’ which, is object identity. The default hashcode() calculates a hash based on the object identity.
When implementing equals: 
	
	Check for same object ‘==’ and return true if so.
	Check that other object is instanceof same class (null returns false here)
	Cast the other object to be an instance of this class
	Call the equals method on each non primitive field of the class and ‘==’ on each primitive. Return false if any comparisons fail.
	Return true.
**Note that using instanceof to check the type of an object will also return true for any child classes. **
**For this reason, using inheritance can complicate the task of implementing equals and hashcode correctly.**

Any fields that are used for comparison should be genuinely relevant - for example do not use a database identifier for equals when this is a field that might be unset until an object is persisted to the database by framework code.
**Equals() must be symmetrical : if A.equals(B) is true then B.equals(A) is true.**
** Equals() must be transitive : if A.equals(B) is true and A.equals(C) is true then C.equals(B) must also be true. **
It can be difficult to get do this correctly, especially when child classes can inherit the equals() method you create. 
**If a child class overrides equals and includes a new field in the comparison, symmetry and transitivity are always broken. **
**(Our Person class illustrates this).** Solutions for this issue are either make the parent class Abstract or **Use Composition over Inheritance**.
 See class ‘WellDesignedPerson’ as an example.

Hashcode is used for mapping objects into ‘buckets’ in various data structures - for example HashMaps and Sets. Implementing hashcode using different fields than are used for equals would result in breaking the Set contract. If hashcode() is calculated on fields which are mutable then data structures containing objects can (and usually will) ‘break’ if those fields are mutated while the object is located in the data structure. 
An example of this - the hashcode of an object maps it to a bucket with value 53 in a HashMap. When the object is to be retrieved from the HashMap, its hashcode() is calculated and then the object is found in bucket 53. If a mutable field used to calculate hashcode changes value, subsequent lookups of the object no longer search for it in bucket 53 of the HashMap.
For hibernate entities etc do not use database IDs that will be modified by the persistence framework as fields used in equals and hashcode.
**A good discussion of implementing equals and hashcode: **

	https://dzone.com/articles/why-should-you-care-about-equals-and-hashcode 

**or read ‘Effectice Java’.**

##Immutability
**As far as possible, design classes to be immutable.** This increases encapsulation and it also ensures thread safety. Only expose set methods on fields when strictly necessary.


##Class invariants
If a class has an invariant (for example ‘name’ must not be null, ‘age’ must be > 10 etc) attempt to enforce this in the constructor of the class, restrict the ability of client code to mutate data in the class as much as possible, either through immutability or through use of methods which enforce the class invariants. Do not expose constructors or methods which break the class invariants even (actually especially) if this is just to make the class more ‘testable’. Design another way to make the class testable - for example perhaps private methods in a class that need to be tested directly by unit tests actually belong in a different class. In this way practicing TDD can help with OOD.

##Copy Constructors
Its good design to have a constructor which takes an instance of the class (or a superclass) and copies it. This allows the constructor to protect class invariants etc and does not require immutability to be broken by exposing set methods. Remember that instances of a class can directly access private members of another instance of the same class - no get or set methods need be exposed for copy constructors to work.

Using factories instead of new
Using factories to decouple client code from concrete instantiations is good practice and an example of Dependency Inversion. In practice we would more likely use a dependency injection framework such as Spring to do this, but classes should not be made into Spring beans just to achieve this.

##Concurrency
###Threading/Synchronized and Volatile. 

###Performance and deadlock.

###Threadsafe collection types. 

Enums.

Serialisation (singleton)

Generics

Concurrency/Threading/Synchronized and Volatile. Threadsafe collection types. Performance and deadlock.

Data Structures - performance for sorting and searching.

Lambdas

Streams

Design patterns

TDD - mockito. Mocks versus stubs.

Corrections on lecture notes from Java course:
Private members are not inherited. 
Static code is not inherited. Specifically - overriding does not happen, instead static methods are bound to the class object. Two static methods with the same signature ‘hide’ each other.
Do not use Vectors for thread safety.
The equals and hashcode example is wrong in multiple ways - equals and hashcode should be based on the same fields, transitive etc. 
They should use immutable fields!




