package phonebook;

public class Main {

    public static void main(String[] args) {

        String dir  = "...\\directory.txt" ;
        String data = "...\\find.txt";

        PBDataOps ops = new PBDataOps(dir, data);

        ops.runOPs();

    }


}
