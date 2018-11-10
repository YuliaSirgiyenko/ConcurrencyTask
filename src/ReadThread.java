public class ReadThread implements Runnable {

    int sum = 0;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                int readItem = BlockingQueueImpl.queue.take();
                //System.out.println(Thread.currentThread().getName() + " take " + readItem);
                sum += readItem;
                System.out.println("Sum of " + Thread.currentThread().getName() + " counts " + sum);

                if (sum >= 100) {
                    System.out.println("Reading thread " + Thread.currentThread().getName() + " is winner");
                    for (Thread thread : BlockingQueueImpl.threadsPool) {
                        thread.interrupt();
                    }
                    BlockingQueueImpl.queue.clear();
                }
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " is interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

}
