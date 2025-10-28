import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    
    public static void main (String [] args) {
        ThreadLocalWithUserContext firstUser = new ThreadLocalWithUserContext(1);
        ThreadLocalWithUserContext secondUser = new ThreadLocalWithUserContext(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();
    }

}

class Context {
    private String userName;

    public Context(String userName) {
        this.userName = userName;
    }
}

class SharedMapWithUserContext implements Runnable {

    public static Map<Integer, Context> userContextPerUserId = new ConcurrentHashMap<>();
    private Integer userId;
    private UserRepository userRepository = new UserRepository();

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(1);
        userContextPerUserId.put(1, new Context(userName));
    }

}

class ThreadLocalWithUserContext implements Runnable {

    private static ThreadLocal<Context> userContext = new ThreadLocal<>();
    private Integer userId;
    private UserRepository userRepository = new UserRepository();

    public ThreadLocalWithUserContext(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContext.set(new Context(userName));
        System.out.println("thread context for given userId: " 
            + userId + " is: " + userContext.get());
    }

}

class UserRepository {

    public String getUserNameForUserId(Integer userId) {
        return "riccardo";
    }

}