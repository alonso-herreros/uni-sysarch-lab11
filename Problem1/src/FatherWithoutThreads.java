class SonWithoutThread{
    private int ident;
    public SonWithoutThread (int id){
        this.ident =id;
    }
    public void run() {
        if (this.ident == 0)
            System.out.println("Hello Luke");
        else
            System.out.println("Skywalker");
    }
}
public class FatherWithoutThreads {
    public static void main(String[] args) {
        SonWithoutThread h1,h2;
        h1 = new SonWithoutThread(0);
        h2 = new SonWithoutThread(1);
        h2.run();
        h1.run();
        System.out.println("I am your father");
    }
}
