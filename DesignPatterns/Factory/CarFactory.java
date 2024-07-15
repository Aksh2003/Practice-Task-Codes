package DesignPatterns.Factory;

public class CarFactory {
    public CarFactory()
    {

    }

    public Car getCar(CarType type)
    {
        switch(type)
        {
            case BMW :
               return new BMWCar();
            case MARUTI:
               return new MarutiCar();
            case MERCEDES:
               return new MercedesCar();
        }
        return null;
    }
}
