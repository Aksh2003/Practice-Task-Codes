package DesignPatterns.Factory;

public class MarutiCar implements Car{

    @Override
    public String getColor() {
        return "BLUE";
    }

    @Override
    public int getPrice() {
       return 1400000;
    }
    
}
