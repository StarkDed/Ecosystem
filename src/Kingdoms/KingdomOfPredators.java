package Kingdoms;

import Bussines_objects.Herbivore;
import Bussines_objects.Predator;

import java.util.ArrayList;

public class KingdomOfPredators implements Runnable{
    private final ArrayList<Predator> predatorsBase; //Основные объекты хищников
    private final ArrayList<Predator> predators;
    private final long timeAppearing;
    private final ArrayList<Herbivore> herbivores;

    public KingdomOfPredators(ArrayList<Predator> predatorsBase, ArrayList<Herbivore> herbivores, ArrayList<Predator> predators, long timeAppearing){
        this.predators=predators;
        this.timeAppearing=timeAppearing;
        this.predatorsBase=predatorsBase;
        this.herbivores=herbivores;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(timeAppearing);
            } catch (InterruptedException e) {
                return;
            }

            if(Thread.currentThread().isInterrupted()){
                return;
            }

            Predator predator=predatorsBase.get((int)(Math.random()*predatorsBase.size())).clonePredator();
            Thread newThread = new Thread(predator);
            predators.add(predator);
            newThread.start();
            System.out.printf("Появилось новый хищник %d (%s) \n", predator.getId(), predator.getName());

            if(Thread.currentThread().isInterrupted()){
                return;
            }
        }
    }

    public void killAllPredators(){ //остановака всех потоков хищников
        for(Predator predator : predators){
            predator.changeAlive();
            predator.changeRunning();
        }

        synchronized (herbivores) {
            herbivores.notifyAll();
        }

        System.out.println("Все хищники погибли");
    }
}
