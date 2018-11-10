public class GenerateThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int writeItem = (int) ((Math.random() * 5) + 1);
                    BlockingQueueImpl.queue.put(writeItem);
                    //System.out.println(Thread.currentThread().getName() + " add " + writeItem);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " is interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

}
