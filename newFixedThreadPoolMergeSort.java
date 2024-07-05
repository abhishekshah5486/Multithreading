import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ListSorter implements Callable<ArrayList<Integer>>{
    public ArrayList<Integer> listToSort;
    public ListSorter(ArrayList<Integer> listToSort){
        this.listToSort = listToSort;
    }

    @Override
    public ArrayList<Integer> call() throws InterruptedException, ExecutionException{
        if (listToSort.size() <= 1) return listToSort;
        int mid = listToSort.size()/2;
        ArrayList<Integer> left = createSubList(listToSort, 0, mid);
        ArrayList<Integer> right = createSubList(listToSort, mid, listToSort.size());
        // Executor service to sort two halves in parallel
        ExecutorService es = Executors.newFixedThreadPool(2);
        ListSorter leftSorter = new ListSorter(left);
        ListSorter righSorter = new ListSorter(right);
        Future<ArrayList<Integer>> leftSortedFuture =  es.submit(leftSorter);
        Future<ArrayList<Integer>> rightSortedFuture = es.submit(righSorter);
        
        ArrayList<Integer> leftSortedList = leftSortedFuture.get();
        ArrayList<Integer> rightSortedList = rightSortedFuture.get();
        return merge(leftSortedList, rightSortedList);
    }
    public ArrayList<Integer> createSubList(ArrayList<Integer> listToSort, int s, int e){
        ArrayList<Integer> lst = new ArrayList<>();
        for (int j=s; j<e; j++){
            lst.add(listToSort.get(j));
        }
        return lst;
    }
    public ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b){
        ArrayList<Integer> sortedList = new ArrayList<>();
        int j = 0;
        int k = 0;
        while (j < a.size() && k < b.size()){
            if (a.get(j) <= b.get(k)){
                sortedList.add(a.get(j));
                j++;
            }
            else{
                sortedList.add(b.get(k));
                k++;
            }
        }
        if (j == a.size()){ 
            while (k < b.size()){
                sortedList.add(b.get(k));
                k++;
            }
        }
        else if (k == b.size()){
            while (j < a.size()){
                sortedList.add(a.get(j));
                j++;
            }
        }
        return sortedList;
    }
}
public class newFixedThreadPoolMergeSort {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ArrayList<Integer> listToSort = new ArrayList<>();
        int[] temp = {9, -1, 0, 4, 7, -3, 8, 3, 12, 3, 9, 8, -10};
        for (int j=0; j<temp.length; j++){
            listToSort.add(temp[j]);
        }
        ListSorter listSorter = new ListSorter(listToSort);
        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<ArrayList<Integer>> ans = es.submit(listSorter);
        System.out.println(ans.get());
    }
}
