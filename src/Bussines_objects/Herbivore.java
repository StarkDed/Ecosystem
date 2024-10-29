package Bussines_objects;

import java.util.ArrayList;


public class Herbivore extends Animal implements Runnable{
    private final ArrayList<Herb> herbs;
    private final long timeEatingFood;
    private volatile boolean running=true;

    public Herbivore(ArrayList<Herb> herbs,String name,long timeMillisecondsHungry,long timeEatingFood){
        super(timeMillisecondsHungry,name);
        this.herbs=herbs;
        this.timeEatingFood=timeEatingFood;
    }

    public void run(){
        while(getAlive() && running){
            Herb herb;
            synchronized (herbs){
                while(herbs.isEmpty() && running){
                    try{
                        herbs.wait();
                    }catch(InterruptedException e){
                        // Дополнить обработку
                        return;
                    }
                }

                if(!this.getAlive()){
                    return;
                }

                if(!this.checkHungry()){
                    System.out.printf("Травоядное животное %d (%s) погибло от голода\n",getId(),getName());
                    return;
                }

                herb=herbs.get((int)Math.random()*herbs.size());
                System.out.printf("Травоядное животное %d (%s) нашло растение %d (%s)\n",getId(),getName(),herb.getId(),herb.getName());
                herbs.remove(herb);
            }

            if(!this.getAlive()){
                return;
            }

            System.out.printf("Травоядное животное %d (%s) кушает растение %d (%s)\n",getId(),getName(),herb.getId(),herb.getName());

            if(!this.getAlive()){
                return;
            }

            try{
                Thread.sleep(timeEatingFood);
            }catch(InterruptedException e){
                return;
            }

            if(!this.getAlive()){
                return;
            }

            System.out.printf("Травоядное животное %d (%s) съело растение %d (%s)\n",getId(),getName(),herb.getId(),herb.getName());

        }
    }

    public void changeRunning(){
        running=false;
    }

    public Herbivore cloneHerbivore(){
        return new Herbivore(herbs,getName(),getTimeMillisecondsHungry(),timeEatingFood);
    }
}
