class Hilo extends Thread {
    int contador = 0;
    static int compartida;

    public void run() {
        synchronized (this) {
            if (contador != 0)
                try {
                    wait();
                } catch (Exception e) {
                }
            contador++;
        }
        compartida++;
        notifyAll();
    }

    public static void main(String[] s) {
        Hilo h1 = new Hilo();
        Hilo h2 = new Hilo();
        Hilo h3 = new Hilo();
        h1.run();
        h2.run();
        h3.run();
    }
}