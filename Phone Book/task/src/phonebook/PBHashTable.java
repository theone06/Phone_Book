package phonebook;

import java.util.ArrayList;
import java.util.Hashtable;

public class PBHashTable {
    private final Hashtable<String, String> phonebook = new Hashtable<>();

   public long fillHashTable(ArrayList<String> list) {
       long creation = System.currentTimeMillis();
       for (String s : list) {
           String key = s.replace("[0-9]", "").strip();
           String value = s.replace("^[0-9]", "").strip();
           phonebook.putIfAbsent(key, value);
       }
       return System.currentTimeMillis() - creation;
   }

   public long searchHashTable(String targetKey) {
       long start = System.currentTimeMillis();
       if (phonebook.containsKey(targetKey)) {
           phonebook.get(targetKey);
           return System.currentTimeMillis() - start;
       } else {
           return System.currentTimeMillis() - start;
       }

   }
}
