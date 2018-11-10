public class GenerateThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            while(BlockingQueueImpl.flag) {
                int writeItem = (int) ((Math.random() * 5) + 1);
                BlockingQueueImpl.queue.put(writeItem);
                //System.out.println(Thread.currentThread().getName() + " add " + writeItem);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished" + Thread.currentThread().getName());
    }

}
