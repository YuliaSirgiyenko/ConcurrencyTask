import java.util.concurrent.TimeUnit;

public class GenerateThread implements Runnable {

    @Override
    public void run() {
        try {
            BlockingQueueImpl.threadPreparation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int writeItem = (int) ((Math.random() * 5) + 1);
                    BlockingQueueImpl.queue.offer(writeItem, 2000, TimeUnit.MILLISECONDS);
                    //System.out.println(Thread.currentThread().getName() + " write " + writeItem);
                    Thread.sleep(200);
                }
            } catch (InterruptedException | NullPointerException e) {
                System.out.println("Interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }

        }
    }

}
