package Kingdoms;

import Bussines_objects.Herb;
import Bussines_objects.Herbivore;

import java.util.ArrayList;

public class KingdomOfHerbivores implements Runnable {
    private ArrayList<Herbivore> herbivoresBase; //Основные объекты хищников
    private final ArrayList<Herbivore> herbivores;
    private final long timeAppearing;
    private final ArrayList<Herb> herbs;

    public KingdomOfHerbivores(ArrayList<Herbivore> herbivoresBase, ArrayList<Herbivore> herbivores, ArrayList<Herb> herbs, long timeAppearing){
        this.herbivores=herbivores;
        this.timeAppearing=timeAppearing;
        this.herbs=herbs;
        this.herbivoresBase=herbivoresBase;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                Thread.sleep(timeAppearing);
            }catch(InterruptedException e){
                return;
            }

            if(Thread.currentThread().isInterrupted()){
                return;
            }

            synchronized (herbivores){
                Herbivore herbivore=herbivoresBase.get((int)(Math.random()*herbivoresBase.size())).cloneHerbivore();
                Thread newThread=new Thread(herbivore);
                herbivores.add(herbivore);
                newThread.start();
                System.out.printf("Появилось новое траводяное животное %d (%s) \n",herbivore.getId(),herbivore.getName());
                herbivores.notifyAll();
            }

            if(Thread.currentThread().isInterrupted()){
                return;
            }
        }

    }

    public void killAllHerbivores(){ //остановака всех травояядных
        for(Herbivore herbivore : herbivores){
            herbivore.changeAlive();
            herbivore.changeRunning();
        }

        synchronized (herbs) {
                herbs.notifyAll();
        }

        System.out.println("Все травоядные животные погибли");
    }
}
