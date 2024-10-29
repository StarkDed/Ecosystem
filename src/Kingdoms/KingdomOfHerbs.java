package Kingdoms;

import Bussines_objects.Herb;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class KingdomOfHerbs implements Runnable{
    private ArrayList<Herb> herbsBase; //Основные объекты трав
    private final long timeAppearing;
    private final ArrayList<Herb> herbs;

    public KingdomOfHerbs(ArrayList<Herb> herbsBase, ArrayList<Herb> herbs, long timeAppearing){
        this.timeAppearing=timeAppearing;
        this.herbs=herbs;
        this.herbsBase=herbsBase;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                TimeUnit.MILLISECONDS.sleep(timeAppearing);
            }catch(InterruptedException e){
                break;
            }

            if(Thread.currentThread().isInterrupted()){
                return;
            }

            Herb herb=herbsBase.get((int)(Math.random()*herbsBase.size())).cloneHerb();
            synchronized (herbs){
                herbs.add(herb);
                System.out.printf("Вырасло новое растение %d (%s). В экосистеме %d растен-ий/e/я \n" , herb.getId(),herb.getName(),herbs.size());
                herbs.notifyAll();
            }

            if(Thread.currentThread().isInterrupted()){
                return;
            }

        }
    }


}
