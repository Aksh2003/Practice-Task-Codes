package DesignPatterns.Observer_.Observer;
import DesignPatterns.Observer_.Observable.*;

public class EmailAlertObserverImpl implements NotificationAlertObserver{

    String emailId;
    StockObservable stockObservable;

    public EmailAlertObserverImpl(String emailId,StockObservable stockObservable){
        this.emailId=emailId;
        this.stockObservable=stockObservable;
    }


    @Override
    public void update() {
        sendMail(emailId,"product is in stock hurry up!!!");
    }

    public void sendMail(String emailId,String msg){
        System.out.println("mail sent to : "+emailId);
    }
}