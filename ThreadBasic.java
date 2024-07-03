class helloWorldPrinter extends Thread{
    @Override
    public void run(){
        System.out.println("Hello World ! " + Thread.currentThread().getName());
    }
}
class numbersPrinter extends Thread{
    @Override
    public void run(){
        int N = 1;
        while (N <= 10){
            System.out.println(N + " " + Thread.currentThread().getName());
            N++;
        }
    }
}
class AlternateThreadNumberPrinter extends Thread{
    int number;
    public AlternateThreadNumberPrinter(int num){
        this.number = num;
    }
    @Override
    public void run(){
        System.out.println(this.number + " " + Thread.currentThread().getName());
    }
}
public class ThreadBasic{
    public static void main(String[] args) {
        // System.out.println(Thread.currentThread().getName());
        // helloWorldPrinter thread1 = new helloWorldPrinter();
        // thread1.start();
        // helloWorldPrinter thread2 = new helloWorldPrinter();
        // thread2.start();
        
        // numbersPrinter numberPrinter1 = new numbersPrinter();
        // numbersPrinter numberPrinter2 = new numbersPrinter();
        // numberPrinter1.start();
        // numberPrinter2.start();

        for (int j=1; j<=100000; j++){
            AlternateThreadNumberPrinter alternateThreadNumberPrinter = new AlternateThreadNumberPrinter(j);
            alternateThreadNumberPrinter.start();
        }

    }
}