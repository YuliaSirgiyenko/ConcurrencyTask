import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/*Describe manager of threads.
* Manager creates definite number of GenerateThreads and ReadThreads.
* With help of countDownLatch manager waits fol all threads readiness.
* After all are threads are ready, they are started simultaneously.
* Manager creates general queue, all threads write and read numbers to/from this queue.*/
public class ThreadManager {

    Collection<Thread> threadsPool = new ArrayList();
    private CountDownLatch countTillAllThreadsStart;
    BlockingQueue<Integer> queue;
    static boolean flag = true;

    /*In constructor:
     - queue with definite capacity is created. Queue will be used for writing and reading numbers by threads;
     - countDownLatch is created as instrument for further simultaneous start of all threads;
     - definite number of GenerateThreads and ReadThreads are created. All threads are added
     to threads collection - threadsPool.*/
    ThreadManager(int numberOfGeneratingThreads, int numberOfReadingThreads, int queueCapacity) {
        this.queue = new ArrayBlockingQueue<>(queueCapacity, true);
        this.countTillAllThreadsStart = new CountDownLatch(numberOfGeneratingThreads + numberOfReadingThreads + 1);

        for (int i = 1; i <= numberOfGeneratingThreads; i++) {
            threadsPool.add(new Thread(new GenerateThread("Writer " + i, countTillAllThreadsStart, queue)));
        }
        for (int j = 1; j <= numberOfReadingThreads; j++) {
            threadsPool.add(new Thread(new ReadThread("Reader " + j, countTillAllThreadsStart, queue)));
        }
    }

    /*Method for simultaneous start of all threads from pool.
    * After start each thread decreases value of countTillAllThreadsStart by 1 and
    * waiting for other threads readiness (event of readiness is countTillAllThreadsStart = 1).
    * During waiting current thread (Thread) is going to sleep for 1000 millis each time.
    * After achievement of event of readiness, CountDownLatch unblocks all threads for working.*/
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
