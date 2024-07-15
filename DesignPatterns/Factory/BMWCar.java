package DesignPatterns.Factory;

public class BMWCar implements Car{

    @Override
    public String getColor() {
        return"GREEN";
    }

    @Override
    public int getPrice() {
        return 120000;
    }
    
}
