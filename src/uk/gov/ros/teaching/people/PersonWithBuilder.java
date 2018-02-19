package uk.gov.ros.teaching.people;

import java.util.Date;

public class PersonWithBuilder implements IPerson {

    public static class PersonBuilder {
        private int age;
        private String name;

        public PersonBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonWithBuilder build() {
            if (age == 0 || name == null) {
                throw new IllegalStateException("Person needs to have name and age before building");
            }
            return new PersonWithBuilder(age, name);
        }
    }

    private int age;
    private String name;
    private Date createdOn;

    private PersonWithBuilder(int age, String name) {
        this.age = age;
        this.name = name;
        this.createdOn = new Date();
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getCreatedOn() {
        return createdOn;
    }
    
    //########### using the PersonWithBuilder class ###########
    public static void main (String [] args) {
        PersonWithBuilder pwb = new PersonWithBuilder.PersonBuilder().setAge(10).setName("Dave").build();
    }

}
