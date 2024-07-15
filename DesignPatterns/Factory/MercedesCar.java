package DesignPatterns.Factory;

public class MercedesCar implements Car {

    @Override
    public String getColor() {
        return "RED";
    }

    @Override
    public int getPrice() {
        return 150000;
    }
    
}
