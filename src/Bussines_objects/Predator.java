package Bussines_objects;

import java.util.ArrayList;

public class Predator extends Animal implements Runnable{
    private final ArrayList<Herbivore> herbivores;
    private final long timeEatingFood;
    private volatile boolean running=true; //флаг для остановки потока

    public Predator(ArrayList<Herbivore> herbivores,String name, long timeMillisecondsHungry,long timeEatingFood){
        super(timeMillisecondsHungry,name);
        this.herbivores=herbivores;
        this.timeEatingFood=timeEatingFood;
    }

    public void run(){
        while(this.getAlive() && running){
            Herbivore herbivore;
            synchronized (herbivores){
                while(herbivores.isEmpty() && running){
                    try{
                        herbivores.wait();
                    }catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if(!this.getAlive()){
                    return;
                }

                if(!this.checkHungry()){
                    System.out.printf("Хищник %d (%s) погиб от голода\n",getId(),getName());
                    return;
                }

                herbivore=herbivores.get((int)Math.random()*herbivores.size());
                System.out.printf("Хищник %d (%s) нашел животное %d (%s)\n",getId(),getName(),herbivore.getId(),herbivore.getName());
                herbivore.changeAlive();
                herbivores.remove(herbivore);
            }

            System.out.printf("Хищник %d (%s) кушает животное %d (%s)\n",getId(),getName(),herbivore.getId(),herbivore.getName());

            if(!this.getAlive()){
                return;
            }

            try{
                Thread.sleep(timeEatingFood);
            }catch(InterruptedException e){

            }

            if(!this.getAlive()){
                return;
            }

            System.out.printf("Хищник %d (%s) съел животное %d (%s)\n",getId(),getName(),herbivore.getId(),herbivore.getName());

            this.setTimeStartHungry();
        }
    }

    public void changeRunning(){
        running=false;
    }

    public Predator clonePredator(){ //клонироваание объекта
        return new Predator(herbivores,getName(),getTimeMillisecondsHungry(),timeEatingFood);
    }

}
