class Hilo extends Thread {
    static int contador = 0; // Changed to `static`
    static int compartida;

    public void run() {
        synchronized (this.getClass()) { // Changed from `this` to `this.getClass()`
            while (contador != 0) // Changed from `if` to `while`
                try {
                    this.getClass().wait();
                } catch (Exception e) {
                }
            contador++;
        }
        compartida++;
        synchronized (this.getClass()) {
            contador--; // Added this decrement to allow other threads to use the resource
            this.getClass().notifyAll(); // Changed to class method (not instance). Moved inside the `synchronized` block
        }
    }

    public static void main(String[] s) {
        Hilo h1 = new Hilo();
        Hilo h2 = new Hilo();
        Hilo h3 = new Hilo();
        // These calls were changed from `run()` to `start()`
        h1.start();
        h2.start();
        h3.start();
    }
}