import java.util.concurrent.TimeUnit;

public class ReadThread implements Runnable {

    int sum = 0;

    @Override
    public void run() {
        try {
            BlockingQueueImpl.threadPreparation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                int readItem = BlockingQueueImpl.queue.poll(2000, TimeUnit.MILLISECONDS);
                //System.out.println(Thread.currentThread().getName() + " read " + readItem);
                sum += readItem;
                System.out.println("Sum of " + Thread.currentThread().getName() + " counts " + sum);

                if (sum >= 100) {
                    System.out.println("Reading thread " + Thread.currentThread().getName() + " is winner");
                    for (Thread thread : BlockingQueueImpl.threadsPool) {
                        thread.interrupt();
                    }
                }
                Thread.sleep(200);
            } catch (InterruptedException | NullPointerException e) {
                System.out.println("Interrupted: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }
    }

}
