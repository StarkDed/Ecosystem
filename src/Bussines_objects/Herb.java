package Bussines_objects;

public class Herb{
    private static long idCount=0;
    private final long id;
    private String name;

    public Herb(String name){
        id=++idCount;
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public long getId(){
        return id;
    }

    public Herb cloneHerb(){
        return new Herb(name);
    }

    public static void setIdCountToNaught(){
        idCount=0;
    }
}
