import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.*;
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

        val.val = 0;
        Lock lock = new ReentrantLock();
        AdderWithLock adderWithLock = new AdderWithLock(val, lock);
        SubtractorWithLock subtractorWithLock = new SubtractorWithLock(val, lock);
        Future<Void> adderWithLockFuture = es.submit(adderWithLock);
        Future<Void> subtractorWithLockFuture = es.submit(subtractorWithLock);
        adderWithLockFuture.get();
        subtractorWithLockFuture.get();
        System.out.println(val.val);

        val.val = 0;
        AdderWithSynchronization adderWithSynchronization = new AdderWithSynchronization(val);
        SubtractorWithSynchronization subtractorWithSynchronization = new SubtractorWithSynchronization(val);
        Future<Void> adderWithSynchronizationFuture = es.submit(adderWithSynchronization);
        Future<Void> subtractorWithSynchronizationFuture = es.submit(subtractorWithSynchronization);
        adderWithSynchronizationFuture.get();
        subtractorWithSynchronizationFuture.get();
        System.out.println(val.val);
        es.shutdown();
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
class AdderWithLock implements Callable<Void>{
    Value v1;
    Lock lock;
    public AdderWithLock(Value v1, Lock lock){
        this.v1 = v1;
        this.lock = lock;
    }
    @Override
    public Void call(){
        for (int j=1; j<=100; j++){
            lock.lock();
            this.v1.val += j;
            lock.unlock();
        }
        return null;
    }
}
class AdderWithSynchronization implements Callable<Void>{
    Value v1;
    public AdderWithSynchronization(Value v1){
        this.v1 = v1;
    }
    @Override
    public Void call(){
        for (int j=1; j<=100; j++){
            synchronized(v1) {
                this.v1.val += j;
            }
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
class SubtractorWithLock implements Callable<Void>{
    Value v1;
    Lock lock;
    public SubtractorWithLock(Value v1, Lock lock){
        this.v1 = v1;
        this.lock = lock;
    }
    @Override
    public Void call(){
        for (int j=1; j<=100; j++){
            lock.lock();
            this.v1.val -= j;
            lock.unlock();
        }
        return null;
    }
}
class SubtractorWithSynchronization implements Callable<Void>{
    Value v1;
    public SubtractorWithSynchronization(Value v1){
        this.v1 = v1;
    }
    @Override
    public Void call(){
        for (int j=1; j<=100; j++){
            synchronized(v1) {
                this.v1.val -= j;
            }
        }
        return null;
    }
}
// Problem of Synchronization