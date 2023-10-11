/**
 * This is one example of wait(), notify() and notifyAll() methods.
 * There is one message class which has message and there are two threads. One is waiter who is waiting for message to print and
 * another is notifier class who will notify the waiting threads.
 *
 * We have created two waiting threads and one notify thread.
 *
 * 1st: When you run the main(), you will see after 5 seconds only one thread will notify and another thread will wait forever
 * because notify can activate only thread from wait state to running state.
 *
 * 2nd: first comment notify() & uncomment notifyAll(). Then you will see both the threads will notify and print the message.
 *
 */

public class WaitNotify {

    public static void main(String[] args) {
        Message msg = new Message("Process it");

        Thread waiter1 = new Waiter(msg,"waiter1");
        Thread waiter2 = new Waiter(msg, "waiter2");
        waiter1.start();
        waiter2.start();

        Thread notifier = new Notifier(msg, "notifier");
        notifier.start();

        System.out.println("All the threads are started");
    }

    private static class Message {
        private String msg;

        public Message(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    private static class Waiter extends Thread {
        private Message message;

        public Waiter(Message message, String name) {
            super(name);
            this.message = message;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            synchronized (message) {
                System.out.println(name+" waiting to get notified at time : "+System.currentTimeMillis());
                try {
                    message.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name+" waiter thread got notified at time : "+System.currentTimeMillis());
                System.out.println(name+" processed : "+message.getMsg());
            }
        }
    }

    private static class Notifier extends Thread {
        private Message message;

        public Notifier(Message message, String name) {
            super(name);
            this.message = message;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name+" started");
            try {
                Thread.sleep(5000);
                synchronized (message) {
                    message.setMsg("Notifier work done");
                    message.notify();
                    //message.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
