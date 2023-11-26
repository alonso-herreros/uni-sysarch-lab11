class CapacityLock {
    private int members = 0;
    private final int max;

    public CapacityLock(int max) {
        this.max = max;
    }

    public synchronized void enter() {
        while (members == max) {
            try { wait(); } catch (InterruptedException e) {}
        }
        members++;
        System.out.println("Someone joined. Members: " + members);
    }
     
    public synchronized void leave() {
        members--;
        System.out.println("Someone left. Members: " + members);
        notifyAll();
    }

    public int getMembers() { return members; }
}


class MyProcess extends Thread {
    final CapacityLock capLock;
    public MyProcess(CapacityLock capLock) {
        this.capLock = capLock;
    }

    public void run() {
        // Other code

        capLock.enter();
        // Cap-locked code
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        capLock.leave();

        // Other code
    }
}


public class Section5_3 {
    public static void main(String[] args) {
        CapacityLock capLock = new CapacityLock(5);
        MyProcess procs[] = new MyProcess[7];

        for (int i=0; i<procs.length; i++) {
            procs[i] = new MyProcess(capLock);
            procs[i].start();
        }

    }
    
}
