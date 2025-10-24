import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class Main {
    
    public static void main (String [] args) throws Exception {
        AsyncFileExample.example();
    }

}

class ReadFileNIO {

    // example but still blocking logic
    public static void example() {
        Path path = Path.of("example.txt");

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(16);

            int bytesRead;
            while ( (bytesRead = fileChannel.read(buffer)) > 0 ) {
                buffer.flip();

                while(buffer.hasRemaining()) {
                    System.out.print( (char) buffer.get() );
                }

                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Hi there!");
    }

}

class AsyncFileExample {

    public static void example () throws Exception {
        Path path = Paths.get("example.txt");
        
        AsynchronousFileChannel channel = 
            AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Future<Integer> result = channel.read(buffer, 0);
        
        while (!result.isDone()) {
            System.out.println("Doing something else, while i'm reading the file");
            for (int i = 0; i < buffer.position(); i++) {
                byte b = buffer.get(i);
                System.out.println((char)b);
            }
            Thread.sleep(50);
        }

        buffer.flip();
        System.out.println("Bytes read: " + result.get());
        while(buffer.hasRemaining()) {
            System.out.print((char)buffer.get());
        }
    }

}