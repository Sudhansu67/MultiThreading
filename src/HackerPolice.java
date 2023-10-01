import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * I want to design a secure vault where I want to store my money. I want to see how long it will take hacker to break into the vault
 * by guessing into my code
 *
 * Steps:
 * We wil have few hacker threads trying to Brute force my code concurrently.
 * We will have police Thread that thread will come for rescue by counting down 10 second and if hackers have not broken into the
 * vault by then and ran way with money, the policemen is going to arrest them.
 */
public class HackerPolice {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        System.out.println("Set Password is "+vault.password);

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingThread(vault));
        threads.add(new DescendingThread(vault));
        threads.add(new PolicaThread());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static class Vault {

        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                //delay the response for potential hackers
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    public static abstract class HackerThread extends Thread {

        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting Thread : "+this.getName());
            super.start();
        }
    }

    public static class AscendingThread extends HackerThread {

        public AscendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName()+" guessed the password "+guess);
                    System.exit(0);
                }
            }
        }
    }

    public static class DescendingThread extends HackerThread {
        public DescendingThread(Vault vault) {
            super(vault);
        }
        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName()+" guessed the password "+guess);
                    System.exit(0);
                }
            }
        }
    }

    public static class PolicaThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Game over for hackers");
            System.exit(0);
        }
    }
}
