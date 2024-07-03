class helloWorldPrinter extends Thread{
    @Override
    public void run(){
        System.out.println("Hello World ! " + Thread.currentThread().getName());
    }
}
public class ThreadBasic{
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        helloWorldPrinter thread1 = new helloWorldPrinter();
        thread1.start();
        helloWorldPrinter thread2 = new helloWorldPrinter();
        thread2.start();
    }
}