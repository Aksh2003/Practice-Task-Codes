package DesignPatterns.Builder;

public class Director {
    StudentBuilder studentBuilder;

    Director(StudentBuilder studentBuilder){
        this.studentBuilder = studentBuilder;
    }

    public Student createStudent(String type){

        if(type.equals("Engineering")){
            return createEngineeringStudent();
        }
        else if(type.equals("MBA")){
            return createMBAStudent();
        }
        return null;
    }


    private Student createEngineeringStudent(){

        return studentBuilder.setRollNumber(1).setAge(21).setName("p1").setCourse("Engineering").build();
    }

    private Student createMBAStudent(){

        return studentBuilder.setRollNumber(2).setAge(24).setName("p2").setFatherName("MyFatherName").setMotherName("MyMotherName").setCourse("MBA").build();

    }


}
