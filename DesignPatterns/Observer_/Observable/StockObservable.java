package DesignPatterns.Observer_.Observable;
import DesignPatterns.Observer_.Observer.*;
public interface StockObservable {

    public void add(NotificationAlertObserver observer);
    public void remove(NotificationAlertObserver observer);
    public  void notifySubscribers();
    public void setStockCount(int newStockAdded);
    public int getStockCount();
}