package phonebook;

import java.util.ArrayList;

public class PBQuicksort {
    private void swap(ArrayList<String> list, int index1, int index2) {
        String temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    private void doSort(ArrayList<String> list, int left, int right) {
        if (left < right) {
            int pivot = partition(list, left, right);
            doSort(list, left, pivot - 1);
            doSort(list, pivot, right);
        }
    }

    private int partition(ArrayList<String> list, int left, int right) {
        int mid = (left + right) / 2;
        String pivot = list.get(mid);

        while (left <= right) {
            while (list.get(left).compareTo(pivot) < 0) {
                ++left;
            }
            while (list.get(right).compareTo(pivot) > 0) {
                --right;
            }
            if (left <= right) {
                swap(list, left, right);
                ++left;
                --right;
            }
        }
        return left;
    }


    public ArrayList<String> quickSort(ArrayList<String> array) {
        doSort(array, 0, array.size() - 1);
        return array;
    }


}

