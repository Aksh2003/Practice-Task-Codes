package DesignPatterns.Strategy;

public class GoodsVehicle extends Vehicle{

    GoodsVehicle(){
        super(new NormalDriverStrategy());
    }
}
