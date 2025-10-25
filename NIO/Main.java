import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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


public class Main {
    
    public static void main (String [] args) throws Exception {
        AsynchronousScannerExample.example();
    }

}

class AsynchronousScannerExample {

    public static void example() throws InterruptedException, ExecutionException, TimeoutException {  
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        Future<String> future = 
            CompletableFuture.supplyAsync( () -> {
                Scanner scanner = new Scanner(System.in);
                String s1 = scanner.nextLine();
                System.out.println("You have entered: " + s1);
                scanner.close();
                return s1;
            }, executorService );

        int i = 1;
        while (i <= 10) {
            System.out.println("I'll close the input in 10 secs: " + String.valueOf(i));
            i = i + 1;
            Thread.sleep(1000);
        }

        System.out.println(future.get());

    }

}

class ListAsynchronousFileChannelWithCompletionHandlerNIO {

    public static void example() throws IOException, InterruptedException {
        System.out.println("hi");
        Path path = Path.of("example1.txt");
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        
        asynchronousFileChannel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer,ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                while(attachment.hasRemaining()) {
                    System.out.print((char)attachment.get());
                }
                System.out.println();
                attachment.clear();
                countDownLatch.countDown();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
                countDownLatch.countDown();
            }
            
        });

        countDownLatch.await();
    } 

}

class ListAsynchronousFileChannelNIO {

    private ConcurrentHashMap<String, Path> paths = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ByteBuffer> buffers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, AsynchronousFileChannel> asynchronousFileChannels = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Future<Integer>> futures = new ConcurrentHashMap<>();

    public void readFile (String filePath) throws IOException {
        Path path = Path.of(filePath);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        Future<Integer> future = asynchronousFileChannel.read(buffer, 0);
        paths.put(filePath, path);
        buffers.put(filePath, buffer);
        asynchronousFileChannels.put(filePath, asynchronousFileChannel);
        futures.put(filePath, future);
    }
    
    /**
     *  cicla su tutto l'hashmap
     *      se un future e' completato
     *          print buffer
     *          rimuovi future
     *          rimuovi path
     *          rimuovi buffer
     *          rimuovi AsynchronousFileChannel
     */
    public void run() {
        while(true) {
            futures.forEach( (key, future) -> {
                if (future.isDone()) {
                    ByteBuffer buffer = buffers.get(key);
                    buffer.flip();
                    System.out.println("reading file: " + key);

                    while (buffer.hasRemaining())
                        System.out.print((char)buffer.get());

                    System.out.println();
                    futures.remove(key);
                    paths.remove(key);
                    asynchronousFileChannels.remove(key);
                    buffers.remove(key);
                }
            } );
        }
    }

    public void runSimple() throws IOException {

        for (int i = 0; i < 10; i++) {
            readFile( "example"+String.valueOf(i)+".txt" );
        }

        while(true) {
            futures.forEach( (key, future) -> {
                if (future.isDone()) {
                    ByteBuffer buffer = buffers.get(key);
                    buffer.flip();
                    System.out.println("reading file: " + key);

                    while (buffer.hasRemaining())
                        System.out.print((char)buffer.get());

                    System.out.println();
                    futures.remove(key);
                    paths.remove(key);
                    asynchronousFileChannels.remove(key);
                    buffers.remove(key);
                }
            } );
        }
    }

}

class TwoAsynchronousFileChannelNIO {

    public static void example() throws Exception {
        Path path1 = Path.of("example1.txt");
        Path path2 = Path.of("example2.txt");

        AsynchronousFileChannel asynchronousFileChannel1 = AsynchronousFileChannel.open(path1, StandardOpenOption.READ);
        AsynchronousFileChannel asynchronousFileChannel2 = AsynchronousFileChannel.open(path2, StandardOpenOption.READ);

        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);

        Future<Integer> future1 = asynchronousFileChannel1.read(buffer1, 0);
        Future<Integer> future2 = asynchronousFileChannel2.read(buffer2, 0);

        future1.get(10, TimeUnit.MILLISECONDS);
        future2.get(10, TimeUnit.MILLISECONDS);

        // simulates join ops in CompletableFuture
        while( !future1.isDone() ) {
            System.out.println("Future1 is working...");
        }

        // simulates join ops in CompletableFuture
        while( !future2.isDone() ) {
            System.out.println("Future2 is working...");
        }

        // now both of thread are completed

        // read buffer1
        System.out.println("Reading buffer 1");
        buffer1.flip();
        while (buffer1.hasRemaining()) {
            System.out.print((char)buffer1.get());
        }
        System.out.println("");

        // read buffer2
        System.out.println("Reading buffer 2");
        buffer2.flip();
        while (buffer2.hasRemaining()) {
            System.out.print((char)buffer2.get());
        }

    }

}

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