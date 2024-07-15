package DesignPatterns.Builder;


public class StudentBuilder {
    int rollNumber;
    int age;
    String name;
    String fatherName;
    String motherName;
    String course;

    public StudentBuilder setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
        return this;
    }

    public StudentBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public StudentBuilder setMotherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public StudentBuilder setCourse(String course) {
        this.course = course;
        return this;
    }
    public Student build() {
        return new Student(this);
    }

}
