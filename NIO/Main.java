import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.Future;

public class Main {
    
    public static void main (String [] args) throws Exception {
        AsynchronousFileChannelNIO.example();
    }

}

/*
 * Buffer Classes
 * 
 * Buffer
 * ByteBuffer
 * ByteOrder
 * CharBuffer
 * DoubleBuffer
 * FloatBuffer
 * IntBuffer
 * LongBuffer
 * MappedByteBuffer
 * ShortBuffer
 */

/*
 * Channel Classes
 * 
 * AsynchrnousChannelGroup
 * Channels
 * SelectableChannel
 * 
 * DatagramChannel
 * 
 * AsynchronousFileChannel
 * FileChannel
 * FileChannel.MapMode
 * FileLock
 * 
 * AsynchronousServerSocketChannel
 * ServerSocketChannel
 * 
 * AsynchronousSocketChannel
 * SocketChannel
 * 
 * Pipe
 * Pipe.SinkChannel
 * Pipe.SourceChannel
 * 
 * MembershipKey
 * 
 * Selector
 */

class AsynchronousFileChannelNIO {

    public static void example() throws Exception {
        Path path = Path.of("example.txt");

        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        
        ByteBuffer buffer = ByteBuffer.allocate(16);
        Future<Integer> result = asynchronousFileChannel.read(buffer, 0);

        while( !result.isDone() ) {
            System.out.println("Reading file asynchronously!");
        }

        System.out.println("Byte read: " + result.get());
        buffer.flip();
        while(buffer.hasRemaining()) {
            System.out.println((char)buffer.get());
        }
        System.out.println(" ");

        buffer.clear();
        asynchronousFileChannel.close();
    }

}

class SimpleIntegerFileNIO {

    public static void example() {
        Path path = Path.of("example.txt");
        IntBuffer buffer = IntBuffer.allocate(16);

        try( FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ) ) {
            
            // int bytesRead = 0;
            // while( (bytesRead = fileChannel.read(buffer)) > 0 ) {
            // }

            int capacity = 10;
            IntBuffer ib = IntBuffer.allocate(capacity);

            ib.put(100);
            ib.put(2,9);

            System.out.println(ib.get(2));

            System.out.println(Arrays.toString(ib.array()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class SimpleReadFileNIO {

    // Channels - Buffer
    public static void example() {
        Path path = Path.of("example.txt");
        ByteBuffer buffer = ByteBuffer.allocate(16);

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            
            int bytesRead = 0;
            while ( (bytesRead = fileChannel.read(buffer)) > 0 ) {
                buffer.flip();
                System.out.println(bytesRead);

                while (buffer.hasRemaining()) {
                    System.out.println((char)buffer.get());
                }

                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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