package uk.gov.ros.teaching.people;
/*
 * Client code should depend on this interface
 */
public interface IPerson {
	int getAge();
	String getName();
	
	public default String print() {
		return "name: "+ getName() + " age:" + getAge();
	}
}
