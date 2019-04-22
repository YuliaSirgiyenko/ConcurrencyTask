import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*Describe thread which generate random numbers and write them to general queue.*/
public class GenerateThread extends AppThread {

    GenerateThread(String name, CountDownLatch countDownLatch, BlockingQueue<Integer> queue) {
        super(name, countDownLatch, queue);
    }

    /*Started thread sends information about start by decrease of number of
    threads in waiting mode by 1. Current thread goes in waiting mode till
    all threads readiness for work.
    After removal of waiting mode, the main logic of method is started -
    thread generate random numbers and write them to general queue*/
    @Override
    public void run() {
        try {
            countDownLatch.countDown();
            countDownLatch.await();

            while(ThreadManager.flag) {
                int writeItem = (int) ((Math.random() * 5) + 1);
                queue.offer(writeItem, 2000, TimeUnit.MILLISECONDS);
                //System.out.println(threadName + " write " + writeItem);
                Thread.sleep(500);
            }
        } catch (InterruptedException | NullPointerException e) {
            System.out.println("Interrupted: " + threadName);
        }
    }

}
