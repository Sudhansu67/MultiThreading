/**
 * If any exception happen during running your thread, then using setUncaughtExceptionHandler() &
 * new Thread.UncaughtExceptionHandler interface we can get the exception and handle it
 *
 */
public class HandleException {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    if (i == 6) {
                        throw new RuntimeException("Internal Exception");
                    }
                    System.out.println("Thread Name : "+Thread.currentThread().getName()+", value : "+i);
                }
            }
        });

        thread.setName("Sudhansu");
        thread.setPriority(Thread.MAX_PRIORITY);
        //Exception handler
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread - "+t.getName()
                    +", the error is "+e.getMessage());
            }
        });
        thread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread Name : "+Thread.currentThread().getName()+", value : "+i);
        }
    }
}
