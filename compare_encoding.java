import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

class compare_encoding{
    public static void main(String[] args) throws IOException{
        System.out.println("----------------------Running Huffman Encoder----------------------------------");
        huffman_encoder.main(args);
        double start1 = System.currentTimeMillis();
        huffman_decoder.decode_file();
        double end1 = System.currentTimeMillis();
        System.out.println("------------------------Running Fano Shannon Encoder----------------------------");
        FanoShannon.main(args);
        double start2 = System.currentTimeMillis();
        huffman_decoder.decode_file();
        double end2 = System.currentTimeMillis();


        String huff_enc = huffman_encoder.encoded;
        String Fano_enc = FanoShannon.encoded;
        String s = huffman_encoder.mystring;

        System.out.println("------------------------------------- Encoded String -----------------------------------");
        System.out.println(huff_enc);
        System.out.println("--------------------------------------------------------------------");
        System.out.println(Fano_enc);
        System.out.println("-------------------------------------  Comparing Average Access Times ---------------------------------");
        double[] act = calc_avg_acces_time(huff_enc, Fano_enc);
        System.out.println();
        System.out.println("::::::: Theoretical ::::::::");
        System.out.println("Average access Time for Huffman is : " +act[0]);
        System.out.println("Average access Time for Fano Shannon is : " + act[1]);
        System.out.println();
        System.out.println("::::::: Actual :::::::::::::");
        System.out.println("Actual average access time for Huffman is : "  + (double)(end1-start1)/s.length() + " milisecs");
        System.out.println("Actual average access time for Fano Shannon  is : "  + (double)(end2-start2)/s.length() + " milisecs");
        System.out.println("::::::: Tree traversal :::::::");
        System.out.println();
        get_tree_access_times();

    }

    public static double[] calc_avg_acces_time(String s1, String s2){
        double[] arr = new double[2];
        String s = huffman_encoder.mystring;
        arr[0] = (double)s1.length() / s.length();
        arr[1] = (double)s2.length() / s.length();
        return arr;
    }
    public static void get_tree_access_times(){
        Node huff_root = huffman_encoder.Root;
        Node fs_root = FanoShannon.Root;
        Map<String, String> huff_map = huffman_encoder.mapping;
        Map<String, String> fs_map = FanoShannon.mapping;
        long t1 = 0;
        Iterator it1 = huff_map.entrySet().iterator();
        System.out.println("---------------------- Huffman Tree ---------------------");
        while(it1.hasNext()){
            Map.Entry p = (Map.Entry)it1.next();
            
            long r =bfs(huff_root, (String)p.getKey());
            System.out.println(p.getKey() + " " + p.getValue() + " -> " + r);
            t1+=r;
        }
        long t2 = 0;
        Iterator it2 = fs_map.entrySet().iterator();
        System.out.println("-------------------- Fano Shannon Tree -----------------");
        while(it2.hasNext()){
            Map.Entry p = (Map.Entry)it2.next();
            double r =bfs(fs_root, (String)p.getKey());
            System.out.println(p.getKey() + " " + p.getValue() + " -> " + r);
            t2+=r;        }
            System.out.println("Actual average access time for Huffman tree is : "  + (long) t1/huff_map.size()  + " nanosecs");
            System.out.println("Actual average access time for Fano Shannon tree is : "  +(long) t2/fs_map.size() + " nannosecs");

    }
    public static long bfs(Node root, String c){
        long start = System.nanoTime();
        bfs_rec(root, c);
        long end = System.nanoTime();
        return   end - start;
    }
    public static boolean bfs_rec(Node n, String c ){

        if (n== null)  return false;  
  
        if (n.value.equals(c))  return true;  
        return bfs_rec(n.left, c) || bfs_rec(n.right, c);  
         
    }
}