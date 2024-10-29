package Main;

import Bussines_objects.Herb;
import Bussines_objects.Herbivore;
import Kingdoms.KingdomOfPredators;
import Bussines_objects.Predator;
import Kingdoms.KingdomOfHerbivores;
import Kingdoms.KingdomOfHerbs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Main{

    public static void main(String[] args) {
        System.out.println("Введите '0' , чтобы завершить программу");
        ArrayList<Herbivore> herbivores=new ArrayList<>();
        ArrayList<Herb> herbs=new ArrayList<>();
        ArrayList<Predator> predators=new ArrayList<>();
        try{
            KingdomOfPredators kingdomOfPredators=getKingdomOfPredators(predators,herbivores);
            KingdomOfHerbivores kingdomOfHerbivores=getKingdomOfHerbivores(herbivores,herbs);
            KingdomOfHerbs kingdomOfHerbs=getKingdomOfHerbs(herbs);
            Ecosystem ecosystem=new Ecosystem(kingdomOfHerbs,kingdomOfHerbivores,kingdomOfPredators);
            ecosystem.start();
        }catch(IOException e){
            System.out.println("Ошибка файла");
        }catch( NumberFormatException | IndexOutOfBoundsException e){
            System.out.println("Неверный ввод данных");
        }

    }

    private static KingdomOfPredators getKingdomOfPredators(ArrayList<Predator> predators, ArrayList<Herbivore> herbivores) throws IOException{
        String s;
        ArrayList<String> list=new ArrayList<>();
        ArrayList<Predator> predatorsBase=new ArrayList<>();

        try(BufferedReader reader= new BufferedReader(new FileReader("filesInput/KingdomOfPredators.txt"))) {

            while ((s = reader.readLine()) != null) {
                list.add(s);
            }

            long timeAppearing = Long.parseLong(list.getFirst().substring(5));

            String[] arr;
            for(int i=1;i<list.size();i++){
                arr=list.get(i).split(",");
                if(arr.length!=3){
                    throw new NumberFormatException();
                }
                String name=arr[0];
                long timeMillisecondsHungry=Long.parseLong(arr[1]);
                long timeEatingFood=Long.parseLong(arr[2]);

                predatorsBase.add(new Predator(herbivores,name,timeMillisecondsHungry,timeEatingFood));
            }

            if(predatorsBase.isEmpty()){
                throw new NumberFormatException();
            }

            return new KingdomOfPredators(predatorsBase,herbivores,predators,timeAppearing);
        }
    }

    private static KingdomOfHerbivores getKingdomOfHerbivores(ArrayList<Herbivore> herbivores, ArrayList<Herb> herbs) throws IOException{
        String s;
        ArrayList<String> list=new ArrayList<>();
        ArrayList<Herbivore> herbivoresBase=new ArrayList<>();

        try(BufferedReader reader= new BufferedReader(new FileReader("filesInput/KingdomOfHerbivores.txt"))) {

            while ((s = reader.readLine()) != null) {
                list.add(s);
            }

            long timeAppearing = Long.parseLong(list.getFirst().substring(5));

            String[] arr;
            for(int i=1;i<list.size();i++){
                arr=list.get(i).split(",");
                if(arr.length!=3){
                    throw new NumberFormatException();
                }
                String name=arr[0];
                long timeMillisecondsHungry=Long.parseLong(arr[1]);
                long timeEatingFood=Long.parseLong(arr[2]);

                herbivoresBase.add(new Herbivore(herbs,name,timeMillisecondsHungry,timeEatingFood));
            }

            if(herbivoresBase.isEmpty()){
                throw new NumberFormatException();
            }

            return new KingdomOfHerbivores(herbivoresBase,herbivores,herbs,timeAppearing);
        }
    }


    private static KingdomOfHerbs getKingdomOfHerbs(ArrayList<Herb> herbs) throws IOException {
        String s;
        ArrayList<String> list=new ArrayList<>();
        ArrayList<Herb> herbsBase=new ArrayList<>();

        try(BufferedReader reader=new BufferedReader(new FileReader("filesInput/KingdomOfHerbs.txt"))){

            while ((s = reader.readLine()) != null) {
                list.add(s);
            }

            long timeAppearing = Long.parseLong(list.getFirst().substring(5));

            for(int i=1;i<list.size();i++){
                String name=list.get(i);
                herbsBase.add(new Herb(name));
            }

            return new KingdomOfHerbs(herbsBase,herbs,timeAppearing);
        }

    }
}