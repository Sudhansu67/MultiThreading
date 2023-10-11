/**
 * Example of ReentrantLock & ReentrantReadWriteLock.
 * The ReentrantReadWriteLock is faster than ReentrantLock.Because provides two types of lock. One is ReadLock & another
 * is WriteLock. So, when readLock is active, multiple threads can read the data and when writeLock is active other
 * WriteLock & ReadLock will be suspended. But in case of ReentrantLock even if you have read or write task, only one thread
 * can do job after getting lock.
 *
 * In this example comment out ReentrantLock and uncomment ReentrantReadWriteLock. You will see the faster execution.
 *
 * Note - In case of multiple write Thread, you might see slower of execution. Choose the Lock wisely based on your requirements.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyReentrantLock {

    private static final String FILE_PATH = "./resources/demo.txt";

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        File file = new File(FILE_PATH);
        List<Thread> threads = new ArrayList<>();

        final ReentrantLock lock = new ReentrantLock();
        threads.add(new ReadThread(file, lock));
        threads.add(new WriteThread(file, lock));
        threads.add(new ReadThread(file, lock));
        threads.add(new ReadThread(file, lock));
        threads.add(new ReadThread(file, lock));
        threads.add(new ReadThread(file, lock));

        /*
        final ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();
        threads.add(new ReadThread(file, readLock));
        threads.add(new WriteThread(file, writeLock));
        threads.add(new ReadThread(file, readLock));
        threads.add(new ReadThread(file, readLock));
        threads.add(new ReadThread(file, readLock));
        threads.add(new ReadThread(file, readLock));
        */

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("Total time : "+(end - start));
    }

    public static class ReadThread extends Thread {
        private File file;
        private Lock lock;

        public ReadThread(File file, Lock lock) {
            this.file = file;
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println("Reading : "+line);
                    Thread.sleep(500);
                    line = reader.readLine();
                }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static class WriteThread extends Thread {
        private File file;
        private Lock lock;

        public WriteThread(File file, Lock lock) {
            this.file = file;
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(file, true));
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    int temp = random.nextInt(99);
                    System.out.println("writing : "+temp);
                    writer.println(temp);
                    Thread.sleep(250);
                }
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
