package Bussines_objects;

import java.util.Date;

public class Animal {
    private boolean alive=true;
    private static long idCount=0;
    private final long id;
    private Date timeStartHungry;
    private final long timeMillisecondsHungry;
    private String name;

    public Animal(long timeMillisecondsHungry,String name){
        this.id=++idCount;
        timeStartHungry=new Date();
        this.timeMillisecondsHungry=timeMillisecondsHungry;
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public long getId(){
        return id;
    }

    public boolean getAlive(){
        return alive;
    }

    public void changeAlive(){
        alive=!alive;
    }

    public long getTimeMillisecondsHungry(){
        return timeMillisecondsHungry;
    }

    public void setTimeStartHungry(){
        this.timeStartHungry=new Date();
    }

    public boolean checkHungry(){
        Date timeNow=new Date();
        long differenceTime=timeNow.getTime()-timeStartHungry.getTime();
        return differenceTime<=timeMillisecondsHungry;
    }

    public static void setIdCountNaught(){
        idCount=0;
    }
}
