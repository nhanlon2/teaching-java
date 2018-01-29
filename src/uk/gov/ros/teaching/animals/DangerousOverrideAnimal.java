package uk.gov.ros.teaching.animals;

public class DangerousOverrideAnimal extends Animal {
	private Integer dangerousModifier;

	public DangerousOverrideAnimal(String type) {
		super(type);
		dangerousModifier = 3;
	}

	@Override
	public int getRequiredRoomTemperature() {
		return super.getRequiredRoomTemperature() + dangerousModifier;//really bad....but why?
	}
}
