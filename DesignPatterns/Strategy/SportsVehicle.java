package DesignPatterns.Strategy;

public class SportsVehicle extends Vehicle{

    public SportsVehicle(){
        super(new SportDriverStrategy());
    }
}
