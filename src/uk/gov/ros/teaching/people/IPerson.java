package uk.gov.ros.teaching.people;

import java.util.Date;

/*
 * Client code should depend on this interface
 */
public interface IPerson {
	int getAge();
	String getName();
	Date getCreatedOn();
	
	public default String print() {
		return "name: "+ getName() + " age:" + getAge();
	}
}
