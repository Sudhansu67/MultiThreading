/**
 * If a thread is executing something and you are calling thread.interrupt() then the thread won't stop.
 * You need to handle Thread.currentThread().isInterrupted() --> if thread is interrupted by outside world then manually
 * you have to return from current thread execution.
 * (or)
 * make the thread as a demon thread
 *
 */

import java.math.BigInteger;

public class InterruptedTask3 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new LongComputationTask(new BigInteger("200000"), new BigInteger("10000000"));
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(10000);
        thread.interrupt();
    }

    private static class LongComputationTask extends Thread {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+" = "+pow(base, power));
            System.exit(0);
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }
    }
}
