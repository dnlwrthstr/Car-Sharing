import java.util.*;     

/** Observable */
interface Observable {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}

/** Concrete Observable */
class RockstarGames implements Observable {

    public String releaseGame;
    /* write your code here ... */
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        // write your code here ...
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(releaseGame);
        }
    }

    public void release(String game) {
        this.releaseGame = game;
        /* write your code here ... */
        notifyObservers();
    }

}

/** Observer */
interface Observer {

    public void update(String domain);
}

/** Concrete Observer */
class Gamer implements Observer{

    private String name;
    private String reaction;
    private Set<String> games = new HashSet<>();

    public Gamer(String name, String reaction) {
        this.reaction = reaction;
        this.name = name;
    }

    /* write your code here ... */
    @Override
    public void update(String game) {
        buyGame(game);
    }

    public void buyGame(String game) {
        games.add(game);
        System.out.println("Inform message to : " + this.name);
        System.out.println(this.name + " says: " + this.reaction);
    }

    @Override
    public String toString() {
        return this.name;
    }    
}

/** Main Class */

public class Main {
    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);

        String game = null;

        Gamer garry = new Gamer("Garry Rose", "I want to pre-order");
        Gamer peter = new Gamer("Peter Johnston", "Pinch me...");
        Gamer helen = new Gamer("Helen Jack", "Jesus, it's new game from Rockstar!");

        /* write your code here ... */
        RockstarGames rockstarGames = new RockstarGames();

        rockstarGames.addObserver(garry);
        rockstarGames.addObserver(peter);
        rockstarGames.addObserver(helen);

        game = scanner.nextLine();
        System.out.println("It's happened! RockstarGames releases new game - " + game + "!");

        /* write your code here ... */
        rockstarGames.release(game);

    }
}