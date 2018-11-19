import java.util.concurrent.TimeUnit;

public class GenerateThread implements Runnable {

    @Override
    public void run() {
        try {
            BlockingQueueImpl.threadPreparation();
            while(BlockingQueueImpl.flag) {
                int writeItem = (int) ((Math.random() * 5) + 1);
                BlockingQueueImpl.queue.offer(writeItem, 2000, TimeUnit.MILLISECONDS);
                //System.out.println(Thread.currentThread().getName() + " write " + writeItem);
                Thread.sleep(500);
            }
        } catch (InterruptedException | NullPointerException e) {
            System.out.println("Interrupted: " + Thread.currentThread().getName());
        }
    }

}
