/**
 * I have list of data which factorial value should be calculated & show the result in main thread.
 * But the problem is as soon as threads are starting we are showing result and of course most of the items shouldn't
 * complete the result and result will be in progress state.
 *
 * To make sure every items result should be calculated before executing mai thread showing result for loop. In that case
 * you have call join() method of every thread.
 *
 * Look at the 1st item of list which is bigger number & it will take more time to calculate. So, you can set the limit time
 * for calculation of recursion value of number. If it exceeds the time, the execution needs to stop and program needs to be
 * terminated gracefully.
 * 1. Set all thread as demon
 * 2. set join(time in milliseconds)
 *
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinCondition {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(10000000L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (FactorialThread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (FactorialThread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of "+inputNumbers.get(i)+" is "+factorialThread.getResult());
            } else {
                System.out.println("The calculation for "+inputNumbers.get(i)+" is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private  BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

    }
}
