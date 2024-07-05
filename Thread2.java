import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Thread2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Value val = new Value(0); 
        Adder adder = new Adder(val);
        Subtractor subtractor = new Subtractor(val);
        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<Void> adderFuture = es.submit(adder);
        Future<Void> subtractorFuture = es.submit(subtractor);
        adderFuture.get();
        subtractorFuture.get();
        System.out.println(val.val);
    }
}
class Value {
    int val;
    public Value(int val){
        this.val = val;
    }
}
class Adder implements Callable<Void>{
    Value v1;
    public Adder(Value v1){
        this.v1 = v1;
    }
    @Override
    public Void call(){
        for (int j=1; j<=100; j++){
            this.v1.val += j;
        }
        return null;
    }
}
class Subtractor implements Callable<Void>{
    Value v1;
    public Subtractor(Value v1){
        this.v1 = v1;
    }
    @Override
    public Void call(){
        for (int j=1; j<=100; j++){
            this.v1.val -= j;
        }
        return null;
    }
}
// Problem of Synchronization