package DesignPatterns.Builder;

public class Main {
    public static void main(String[] args) {
        Director dir = new Director(new StudentBuilder());
        Student engineerStudent = dir.createStudent("Engineering");
        Student mbaStudent =dir.createStudent("MBA");

        System.out.println(engineerStudent);
        System.out.println(mbaStudent);


    }
}
