public class Demo {

    /*Demonstration of threadManager working*/
    public static void main(String[] args) throws InterruptedException {

        ThreadManager threadManager = new ThreadManager(5, 2, 10);
        threadManager.launchAllThreads();

    }

}
