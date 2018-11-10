import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueImpl {

    static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20, true);
    static ArrayList<Thread> threadsPool = new ArrayList(7);

    void init(){
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new GenerateThread(), "Writer " + i);
            t.start();
            threadsPool.add(t);
        }
        for (int j = 1; j < 3; j++){
            Thread t = new Thread(new ReadThread(), "Reader " + j);
            t.start();
            threadsPool.add(t);
        }
    }

}
