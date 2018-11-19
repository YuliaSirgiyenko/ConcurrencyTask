import java.util.concurrent.TimeUnit;

public class ReadThread implements Runnable {

    int sum = 0;

    @Override
    public void run() {
        try {
            BlockingQueueImpl.threadPreparation();
            while (BlockingQueueImpl.flag) {
                int readItem = BlockingQueueImpl.queue.poll(2000, TimeUnit.MILLISECONDS);
                //System.out.println(Thread.currentThread().getName() + " read " + readItem);
                sum += readItem;
                System.out.println("Sum of " + Thread.currentThread().getName() + " counts " + sum);

                if (sum >= 100 && BlockingQueueImpl.flag) {
                    BlockingQueueImpl.flag = false;
                    System.out.println("Reading thread " + Thread.currentThread().getName() + " is winner");
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException | NullPointerException e) {
            System.out.println("Interrupted: " + Thread.currentThread().getName());
        }
    }

}
