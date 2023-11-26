
class SyncedCounter {
    private int value;

    public SyncedCounter(int value) { this.value = value; }

    public synchronized void increase() {
        value++;
        notifyAll();
    }
    public synchronized void waitValue(int val) {
        while (this.value != val) {
            try { wait(); } catch (InterruptedException e) {}
        }
    }
}

class MyProcess extends Thread {
    SyncedCounter counter;

    public MyProcess(SyncedCounter counter) {
        this.counter = counter;
    }
    
    public void run() {
        synchronized(MyProcess.class) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            counter.increase();
        }
    }
}

public class Section5_2 {

    public static void main(String[] args) {
        SyncedCounter counter = new SyncedCounter(0);
        MyProcess C1 = new MyProcess(counter);
        MyProcess C2 = new MyProcess(counter);
        MyProcess C3 = new MyProcess(counter);
        C1.start();
        C2.start();
        C3.start();

        counter.waitValue(3);
    }

}
