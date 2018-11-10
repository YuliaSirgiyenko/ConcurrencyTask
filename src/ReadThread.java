public class ReadThread implements Runnable {

    int sum = 0;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            while (BlockingQueueImpl.flag) {
                int readItem = BlockingQueueImpl.queue.take();
                //System.out.println(Thread.currentThread().getName() + " take " + readItem);
                sum += readItem;
                System.out.println("Sum of " + Thread.currentThread().getName() + " counts " + sum);

                if (sum >= 100) {
                    System.out.println("Reading thread " + Thread.currentThread().getName() + " is winner");
                    BlockingQueueImpl.flag = false;
                    BlockingQueueImpl.queue.clear();
                }
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished" + Thread.currentThread().getName());
    }

}
