package Main;

import Bussines_objects.Animal;
import Bussines_objects.Herb;
import Kingdoms.KingdomOfPredators;
import Kingdoms.KingdomOfHerbivores;
import Kingdoms.KingdomOfHerbs;

import java.util.Scanner;

public class Ecosystem{
    private final static Scanner sc=new Scanner(System.in);
    private final KingdomOfHerbivores kingdomOfHerbivores;
    private final KingdomOfHerbs kingdomOfHerbs;
    private final KingdomOfPredators kingdomOfPredator;

    public Ecosystem(KingdomOfHerbs kingdomOfHerbs, KingdomOfHerbivores kingdomOfHerbivores, KingdomOfPredators kingdomOfPredator){
        this.kingdomOfHerbivores=kingdomOfHerbivores;
        this.kingdomOfHerbs=kingdomOfHerbs;
        this.kingdomOfPredator=kingdomOfPredator;
    }

    public void start(){
        Animal.setIdCountNaught();
        Herb.setIdCountToNaught();
        Thread thread=new Thread(kingdomOfHerbivores);
        Thread thread2=new Thread(kingdomOfHerbs);
        Thread thread3=new Thread(kingdomOfPredator);
        thread.start();
        thread2.start();
        thread3.start();

        String command;
        while(true){
            command=sc.nextLine();
            if(command.equals("0")){ //условние остановки программы
                thread.interrupt();
                thread2.interrupt();
                thread3.interrupt();
                stop();
                break;
            }
        }
    }

    private void stop(){ // метод остановки
        System.out.println("Дождитесь завершения программы");
        kingdomOfPredator.killAllPredators();
        kingdomOfHerbivores.killAllHerbivores();
        System.out.println("Прогрмма завершена");
    }

}
