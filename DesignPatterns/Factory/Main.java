package DesignPatterns.Factory;

public class Main {
    public static void main(String[] args) {
    CarType type = CarType.BMW;
    CarFactory factory = new CarFactory();
    Car car = factory.getCar(type);
    System.out.println("the car "+type+ " has color of "+car.getColor()+" and price of "+car.getPrice());
    }
    
}
