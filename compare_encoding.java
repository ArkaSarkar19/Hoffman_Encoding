import java.io.IOException;
import java.math.BigDecimal;


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
        System.out.println("::::::: Theoretical ::::::::");
        System.out.println("Average access Time for Huffman is : " +act[0]);
        System.out.println("Average access Time for Fano Shannon is : " + act[1]);
        System.out.println("::::::: Actual :::::::::::::");
        System.out.println("Actual average access time for Huffman is : "  + (double)(end1-start1)/s.length() + " milisecs");
        System.out.println("Actual average access time for Fano Shannon  is : "  + (double)(end2-start2)/s.length() + " milisecs");

    }

    public static double[] calc_avg_acces_time(String s1, String s2){
        double[] arr = new double[2];
        String s = huffman_encoder.mystring;
        arr[0] = (double)s1.length() / s.length();
        arr[1] = (double)s2.length() / s.length();
        return arr;
    }
}