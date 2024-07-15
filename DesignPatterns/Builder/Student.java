package DesignPatterns.Builder;


public class Student {
    int rollNumber;
    int age;
    String name;
    String fatherName;
    String motherName;
    String course;
    public Student(StudentBuilder builder){
        this.rollNumber = builder.rollNumber;
        this.age = builder.age;
        this.name = builder.name;
        this.fatherName = builder.fatherName;
        this.motherName = builder.motherName;
        this.course = builder.course;
    }

    public String toString(){
        return ""+ " roll number: " + this.rollNumber +
                " age: " + this.age +
                " name: " + this.name +
                " father name: " + this.fatherName +
                " mother name: " + this.motherName +
                " course name:"+ this.course;
    }


}
