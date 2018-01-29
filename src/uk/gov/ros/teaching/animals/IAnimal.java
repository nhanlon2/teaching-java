package uk.gov.ros.teaching.animals;

public interface IAnimal {
	String getAnimalType();
	/*
	 * Animals must be kept at this temperature
	 */
	int getRequiredRoomTemperature();
}
