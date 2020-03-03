import java.util.*;
public class ass3_prob1{
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();
        int[] pp = new int[n];
        int[] main = new int[n];
        for(int i=0;i<n;i++){
            pp[i] = stdin.nextInt();
        }
        for(int i=0;i<n;i++){
            main[i] = stdin.nextInt();
        }
        System.out.println(completion_time(pp, main));

    }
    public static int completion_time(int[] pp, int[] main){
        int t = 0;
        int curr = 0;
        int[] buffer = new int[pp.length+1];
        for(int i=0;i<pp.length;i++){
            t+=pp[i];
            curr = pp[i];
            for(int j=0;j<buffer.length;j++){
                buffer[i] = buffer[i] - curr;
                if(buffer[i] < 0) buffer[i] = 0;
            }
            buffer[i+1] = main[i];
        }
        for(int i=0;i<buffer.length;i++){
            t+=buffer[i];
        }
        return t;

    }
}