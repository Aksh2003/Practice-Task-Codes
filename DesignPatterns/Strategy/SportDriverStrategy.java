package DesignPatterns.Strategy;

public class SportDriverStrategy implements DriveStrategy{

    @Override
    public void drive() {
        System.out.println("sports drive strategy");
    }
}
