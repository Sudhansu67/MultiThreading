/**
 * If a thread is not executing code and just sleeping, then thread will be interrupted if call thread.interrupt() method
 *
 */

public class InterruptedTask1 {
    public static void main(String[] args) {
        Thread thread = new BlockingTask();
        thread.start();
        thread.interrupt();
    }

    public static class BlockingTask extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking Thread");
            }
        }
    }
}
