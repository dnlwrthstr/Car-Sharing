import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Observable interface
 **/
interface Observable {
    // write your code here ...
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}

/**
 * Concrete Observable - Rockstar Games
 **/
class RockstarGames implements Observable {

    public String releaseGame;

    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void releaseNewVideo(String video) {
        System.out.println("Release new video : " + video);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            System.out.println("Notification for gamer : " + observer);
            observer.update(releaseGame);
        }
    }

    // write your code here ...

    public void release(String game) {
        this.releaseGame = game;
        // write your code here ..
        notifyObservers();
    }
}

/**
 * Observer interface
 **/
interface Observer {
    // write your code here ...
    public void update(String domain);
}

/**
 * Concrete observer - Gamer
 **/
class Gamer implements Observer {

    private String name;
    private Set<String> games = new HashSet<>();

    public Gamer(String name) {
        this.name = name;
    }

    // write your code here ...
    @Override
    public void update(String game) {
        buyGame(game);
    }

    public void release(String game) {
        System.out.println("Notification for gamer : " + name);
        buyGame(name);
    }

    public void buyGame(String game) {
        if(games.contains(game)) {
            System.out.println("What? They've already released this game ... I don't understand");
        } else {
            System.out.println(name + " says : \"Oh, Rockstar releases new game " + game + " !\"");
            games.add(game);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}

/**
 * Main class
 **/
public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        String game = null;

        RockstarGames rockstarGames = new RockstarGames();

        Gamer garry = new Gamer("Garry Rose");
        Gamer peter = new Gamer("Peter Johnston");
        Gamer helen = new Gamer("Helen Jack");

        rockstarGames.addObserver(garry);
        rockstarGames.addObserver(peter);
        rockstarGames.addObserver(helen);

        for (int i = 0; i < 2; i++) {
            game = scanner.nextLine();
            rockstarGames.release(game);
        }
    }
}