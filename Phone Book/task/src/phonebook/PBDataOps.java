package phonebook;

import java.util.ArrayList;

public class PBDataOps {
    PhoneBookIO io = new PhoneBookIO();
    PBHashTable hash = new PBHashTable();
    ArrayList<String> dir;
    ArrayList<String> target;
    private long jumpstart;
    private String halt = "";
    private long hashTime;

    /* Constructor */
    public PBDataOps(String dirFile, String targetFile) {
     target = io.readFile(targetFile);
     dir = dataFormatter(io.readFile(dirFile));
    }

    //methods to format data into a manageable state
    public ArrayList<String> dataFormatter(ArrayList<String> list) {
        ArrayList<String> temp = new ArrayList<>();
        for (String s : list) {
            temp.add(stringRotate(s).strip());
        }
        return temp;
    }

    public String stringRotate(String s1) {
        return (s1.replaceAll("[0-9]", "") + " " + s1.replaceAll("[^0-9]", ""));
    }

    //Tests
    public void runOPs() {
        System.out.println("Start searching (linear search)...");
        long l = linearSearch();
        TimeConverter con = new TimeConverter();
        System.out.println(con.convertMilli(l) + "\n");
        long totalTime;

        System.out.println("Start searching (bubble sort + jump search)...");
        long sortTime = bubbleSort(l);
        int count = 0;
        long timeElapsed = 0;

        if (!halt.isEmpty()) {
            //use linear
            timeElapsed += linearSearch();
            totalTime = timeElapsed + sortTime;
            System.out.println( con.convertMilli(totalTime) );
        } else {
            for (String s1 : target) {
                timeElapsed +=  jumpSearch(dir, s1);
                count++;
            }
            totalTime = timeElapsed + sortTime;
            System.out.println("Found " + count + " / " + target.size() + " entries. Time taken: " + con.convertMilli(totalTime) );

        }
        System.out.println("Sorting time: " + con.convertMilli(sortTime) + halt);
        System.out.println("Searching time: " + con.convertMilli(timeElapsed) + "\n");

        //quicksort
        System.out.println("Starting search (quick sort + binary search)...");
        int bCount = 0;
        PBQuicksort qs = new PBQuicksort();
        long qStart = System.currentTimeMillis();
        qs.quickSort(dir);
        long qDone = System.currentTimeMillis() - qStart;
        //count entries found
        for (String s : target) {
            timeElapsed += binarySearch(s);
            bCount++;
        }
        System.out.println("Found " + bCount + " / " + target.size() + " entries. Time taken: " + con.convertMilli(qDone + timeElapsed));
        System.out.println("Sorting time: " + con.convertMilli(qDone));
        System.out.println("Searching time: " + con.convertMilli(timeElapsed) + "\n");

        //Using a hash table
        System.out.println("Starting search (hash table)...");
        long init = hash.fillHashTable(dir);
        int htCount = 0;
        for (String name : target) {
          hashTime +=  hash.searchHashTable(name);
          htCount++;
        }
        System.out.println("Found " +  htCount + " / " + target.size() + " entries. Time taken: " + con.convertMilli(hashTime + init));
        System.out.println("Creating time: " + con.convertMilli(init));
        System.out.println("Searching time: " + con.convertMilli(hashTime));



    }

    //A linear search method

    public long linearSearch() {
        long linearStart;
        int count = 0;

        linearStart = System.currentTimeMillis();
        for (String entry : target) {
            for (String dir : dir) {
                if (dir.startsWith(entry + " ")) {
                    count++;
                }
            }
        }
        long time = (System.currentTimeMillis() - linearStart);
        System.out.print("Found " + count + " / " + target.size()
                + " entries. Time taken: " );
        return time;
    }

    public long bubbleSort (long linTime) {
        long bubbleStart = System.currentTimeMillis();
        long elapsed;
        String temp;

        for (int i = 0; i < dir.size(); i++) {
             for (int j = 0; j < dir.size() - i - 1; j++) {
                if (dir.get(j).compareTo(dir.get(j + 1)) > 0) {
                    temp = dir.get(j);
                    dir.set(j, dir.get(j + 1));
                    dir.set(j + 1, temp);
                }
            }
            elapsed = System.currentTimeMillis() - bubbleStart;
            if (elapsed > (linTime * 10)) {
                halt = "- STOPPED, moved to linear search";
                return elapsed;
            }
        }
        return System.currentTimeMillis() - bubbleStart;
    }

    public long jumpSearch(ArrayList<String> dataSet, String target) {
        int currentRight = 0; // right border of the current block
        int prevRight = 0; // right border of the previous block


        jumpstart = System.currentTimeMillis();
        /* If array is empty, the element is not found */
        if (dataSet.isEmpty()) {
            return System.currentTimeMillis() - jumpstart;
        }

        /* Check the first element */
        if (dataSet.get(currentRight).equals(target)) {
            return System.currentTimeMillis() - jumpstart;
        }

        /* Calculating the jump length over array elements */
        int jumpLength = (int) Math.sqrt(dataSet.size());

        /* Finding a block where the element may be present */
        while (currentRight < dataSet.size() - 1) {

            /* Calculating the right border of the following block */
            currentRight = Math.min(dataSet.size() - 1, currentRight + jumpLength);

            if (dataSet.get(currentRight).compareTo(target) >= 0) {
                break; // Found a block that may contain the target element
            }

            prevRight = currentRight; // update the previous right block border
        }

        /* If the last block is reached, and it cannot contain the target value => not found */
        if ((currentRight == dataSet.size() - 1) && dataSet.get(currentRight).compareTo(target) < 0) {
            return System.currentTimeMillis() - jumpstart;
        }

        /* Doing linear search in the found block */
        return backwardSearch(dataSet, target, prevRight, currentRight);
    }

    public long backwardSearch(ArrayList<String> data, String target, int leftExcl, int rightIncl) {
        for (int i = rightIncl; i > leftExcl; i--) {
            if (data.get(i).contains(target)) {
               return System.currentTimeMillis() - jumpstart;
            }
        }
        return System.currentTimeMillis() - jumpstart;
    }

    public long binarySearch(String target) {
        long start = System.currentTimeMillis();
        int lIndex = 0;
        int rIndex = dir.size() -1;
        int mIndex;

        if (lIndex > rIndex){
            return System.currentTimeMillis() - start;
        }

        while(lIndex <= rIndex) {
            mIndex =(lIndex + rIndex)/2;
            if (dir.get(mIndex).compareTo(target) < 0) {
                lIndex = mIndex + 1;
            } else if (dir.get(mIndex).compareTo(target) > 0) {
                rIndex = mIndex - 1;
            } else {
                return System.currentTimeMillis() - start;
            }
        }
        return System.currentTimeMillis() - start;
    }



}




