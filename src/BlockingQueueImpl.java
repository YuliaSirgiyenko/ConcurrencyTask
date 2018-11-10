import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueImpl {

    static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20, true);
    static boolean flag = true;

    void init(){
        for (int i = 1; i < 6; i++) {
            (new Thread(new GenerateThread(), "Writer " + i)).start();
        }
        for (int j = 1; j < 3; j++){
            (new Thread(new ReadThread(), "Reader " + j)).start();
        }
    }

}
