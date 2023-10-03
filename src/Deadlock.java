/**
 *  we have two railway track. Road-A & Road-B. When TrainA is passing road-A, then train-B has to wait in road-B
 *  for junction crossing.
 *
 *  In this case we should have two object locks. When TrainA is crossing, TrainA should lock first ObjectA & then ObjectB.
 *  TrainA
 *  ------
 *  lock(ObjectA)
 *  lock(ObjectB)
 *
 *  TrainB
 *  ------
 *  lock(objectB)
 *  lock(objectA)
 *
 *  So, in above condition, there might be chance where both objects are locked and waiting for each other.
 *
 *  Best Solution(lock the objects in same order. First get the opposite object. If you get the object then deadlock condition never occurs):
 *  TrainA
 *  ------
 *  lock(ObjectA)
 *  lock(ObjectB)
 *
 *  TrainB
 *  ------
 *  lock(ObjectA)
 *  lock(ObjectB)
 *
 */

public class Deadlock {

    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();
    }

    private static class TrainB implements Runnable {
        private Intersection intersection;

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                intersection.takeRoadB();
            }
        }
    }

    private static class TrainA implements Runnable {
        private Intersection intersection;

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                intersection.takeRoadA();
            }
        }
    }

    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is blocked by thread "+Thread.currentThread().getName());

                synchronized (roadB) {
                    System.out.println("Train is passing through Road A");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (roadA) {
            //synchronized (roadB) {
                System.out.println("Road B is blocked by thread "+Thread.currentThread().getName());

                synchronized (roadB) {
                //synchronized (roadA) {
                    System.out.println("Train is passing through Road B");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
