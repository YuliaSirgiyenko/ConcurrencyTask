import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class ThreadManager {

    Collection<Thread> threadsPool = new ArrayList();
    private CountDownLatch countTillAllThreadsStart;
    BlockingQueue<Integer> queue;
    static boolean flag = true;

    ThreadManager(int numberOfGeneratingThreads, int numberOfReadingThreads, int queueCapacity) {
        this.queue = new ArrayBlockingQueue<>(queueCapacity, true);
        this.countTillAllThreadsStart = new CountDownLatch(numberOfGeneratingThreads+numberOfReadingThreads+1);

        for (int i = 1; i <= numberOfGeneratingThreads; i++) {
            GenerateThread currentThread = new GenerateThread("Writer " + i, countTillAllThreadsStart, queue);
            threadsPool.add(new Thread(currentThread));
            countTillAllThreadsStart = currentThread.countDownLatch;
        }

        for (int j = 1; j <= numberOfReadingThreads; j++) {
            ReadThread currentThread = new ReadThread("Reader " + j, countTillAllThreadsStart, queue);
            threadsPool.add(new Thread(currentThread));
            countTillAllThreadsStart = currentThread.countDownLatch;
        }
    }

    void launchAllThreads() {
        try {
            for (Thread thread : threadsPool) {
                thread.start();
            }

            while (countTillAllThreadsStart.getCount() != 1) {
                    Thread.sleep(1000);
                }
            System.out.println("All threads ready");
            countTillAllThreadsStart.countDown();

        } catch (InterruptedException e) {
            System.out.println("BlockingQueue is interrupted.");
        }
    }

}
