package uk.gov.ros.teaching.animals;

//There is no public class in this .java compilation unit.
//Client code (outside this package) will not be aware that these animal types exist - this provides encapsulation at the
//package level
class SlowAnimal extends Animal{
	
	public SlowAnimal(String type){
		
	}
	
	@Override
	public int getRequiredRoomTemperature() {
		return super.getRequiredRoomTemperature()+5;
	}
}