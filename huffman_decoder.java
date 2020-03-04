import java.util.*;
import java.io.*;
public class huffman_decoder{
    public static void main(String[] args) throws IOException{
        System.out.println("------------------------Decoding-----------------------");

        decode_file();
    }

    public static void decode_file() throws IOException{
        File file = new File("mapping.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        Map<String, String> map = new HashMap<String, String>(); 
        while ((line = bufferedReader.readLine()) != null) {
            String val = Character.toString(line.charAt(1));
            String key = line.substring(7,line.length());
            System.out.println("Key : " + key + " |  value : " + val);
            map.put(key, val);
        }

        File f = new File("encoded.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String l;
        String s = "";
        while((l=br.readLine())!=null){
            s+=l;
        }

        String curr_enc = "";  
        String new_string = "";
        
        for(int i=0;i<s.length();i++){
            curr_enc+=Character.toString(s.charAt(i));
            if(map.containsKey(curr_enc)){
                new_string+=map.get(curr_enc);
                curr_enc = "";
            }
        }

        System.out.println("New String : " + new_string);

    }
}