import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
class SingleNumberPrinter extends Thread{
    static int num = 0;
    @Override
    public void run(){
        num++;
        System.out.println(num + " " + Thread.currentThread().getName());
    }
}
class Student implements Runnable{
    int num;
    public Student(int num){
        this.num = num;
    }
    @Override
    public void run() {
        System.out.println(this.num + " " + Thread.currentThread().getName());
    }
}
class DoubleList implements Runnable{
    public List<Integer> arr;
    public DoubleList(List<Integer> arr){
        this.arr = arr;
    }
    public List<Integer> returnDoubledList(List<Integer> arr){
        return arr;
    }
    @Override
    public void run(){
        List<Integer> ans = new ArrayList<>();
        for (int j=0; j<this.arr.size(); j++){
            ans.add(this.arr.get(j) * 2);
        }
        returnDoubledList(ans);
    }
}

// Callable Interface to be used to return something by a thread
class ListDoubler implements Callable<ArrayList<Integer>>{
    public ArrayList<Integer> lst;

    public ListDoubler(ArrayList<Integer> lst){
        this.lst = lst;
    }
    @Override
    public ArrayList<Integer> call(){
        ArrayList<Integer> ans = new ArrayList<>();
        for (int j=0; j<this.lst.size(); j++){
            ans.add(this.lst.get(j) * 2);
        }
        return ans;
    }
    
}
public class ThreadBasic{
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // System.out.println(Thread.currentThread().getName());
        // helloWorldPrinter thread1 = new helloWorldPrinter();
        // thread1.start();
        // helloWorldPrinter thread2 = new helloWorldPrinter();
        // thread2.start();
        
        // numbersPrinter numberPrinter1 = new numbersPrinter();
        // numbersPrinter numberPrinter2 = new numbersPrinter();
        // numberPrinter1.start();
        // numberPrinter2.start();

        // for (int j=1; j<=100000; j++){
        //     AlternateThreadNumberPrinter alternateThreadNumberPrinter = new AlternateThreadNumberPrinter(j);
        //     alternateThreadNumberPrinter.start();
        // }

        // for (int j=0; j<100; j++){
        //     SingleNumberPrinter singleNumberPrinter = new SingleNumberPrinter();
        //     singleNumberPrinter.start();
        // }

        // Thread th = new Thread(new Student(0));
        // th.start();

        // for(int j=1; j<=100; j++){
        //     Thread temp = new Thread(new Student(j));
        //     temp.start();
        // }

        // Executor Service java
        // ExecutorService es = Executors.newFixedThreadPool(10);
        // for (int j=1; j<=100; j++){
        //     es.submit(new Student(j));
        // }

        ExecutorService es = Executors.newFixedThreadPool(10);
        ArrayList<Integer> lst = new ArrayList<>();
        for (int j=0; j<10; j++){
            lst.add(j);
        }
        ListDoubler listDoubler = new ListDoubler(lst);
        Future<ArrayList<Integer>> doubledList = es.submit(listDoubler);
        System.out.println(doubledList.get());
    }
}