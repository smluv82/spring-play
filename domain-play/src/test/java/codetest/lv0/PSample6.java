package codetest.lv0;

public class PSample6 {

    public int solution(int storage, int usage, int[] change) {
        int total_usage = usage;
        for(int i=0; i<change.length; i++){

//            usage = total_usage * change[i] / 100;
            usage = usage * change[i] / 100;

            total_usage += usage;
            if(total_usage > storage){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        PSample6 p = new PSample6();
        int storage = 5141;
        int usage = 500;
        int[] change = new int[]{10, -10, 10, -10, 10, -10, 10, -10, 10, -10};


//        int storage = 1000;
//        int usage = 2000;
//        int[] change = new int[]{-10, 25, -33];

        var result = p.solution(storage, usage, change);
        System.out.println("result : " + result);
    }
}
