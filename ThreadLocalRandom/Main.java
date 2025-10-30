import java.util.concurrent.ThreadLocalRandom;

public class Main {
    
    public static void main (String [] args) {
        int boundedRandomValue = ThreadLocalRandom.current().nextInt(10);
        System.out.println(boundedRandomValue);
    }

}
