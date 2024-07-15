package DesignPatterns.Strategy;

public class NormalDriverStrategy implements DriveStrategy{

    @Override
    public void drive(){
        System.out.println("normal drive strategy");
    }
}