import java.util.*;
import java.io.*;
public class FanoShannon{
    public static String mystring = null;
    public static Map<String, String> mapping = new HashMap<String,String>();
    public static String encoded;

    public static void main(String[] args) throws IOException {
        File reader = new File("input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(reader));
        String line;
        String s = ""; 
        while ((line = bufferedReader.readLine()) != null) {
            s+=line;
        }
        mystring = s;
        System.out.println(s);
        ArrayList<Node> a = gen_nodes(s);
        if(a==null) System.exit(0);
        a.forEach(x ->{
            System.out.println(x.value + " "  + x.freq);
        });

        Node root = gen_fano_shannon_tree(a);
        System.out.println("-----------------Level order traversal-------------");
        printLevelOrder(root);
        System.out.println("------------------------Encoding----------------------");
        get_encoding(root);
        System.out.println("------------------------Decoding-----------------------");

        decode_file();

    }
    public static ArrayList<Node> gen_nodes(String s){
        ArrayList<Node> arr = new ArrayList<Node>();
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        if(s!=null){
            for(int i=0;i<s.length();i++){
                if(!map.containsKey(s.charAt(i))){
                    map.put(s.charAt(i), 0);
                }
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
        }
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry p = (Map.Entry)it.next();
            Node add = new Node(Character.toString((char)p.getKey()),(int)p.getValue());
            arr.add(add);

        }
        return (s==null) ? null : arr;   

    }

    public static Node gen_fano_shannon_tree(ArrayList<Node> node){
        ArrayList<Node> list = node;
        Collections.sort(list, new freq_comparator());
        for(int j=0;j<node.size();j++) node.get(j).display();        
        return rec_fst(node, 0);
    }
    public static Node rec_fst(ArrayList<Node> arr, int k){
        if(arr.size() <= 1) return arr.get(0);
        if(arr.size() == 2) {
            Node add = new Node("A*", arr.get(0).freq + arr.get(1).freq);
            add.left = arr.get(0);
            add.right = arr.get(1);
            return add;
        }
        int total_freq = 0;
        for(int j=0;j<arr.size();j++) total_freq+=arr.get(j).freq;
        int n = total_freq/2;
        int i=0;
        int curr_freq=0;
        for(i=0;i<arr.size();i++){
            curr_freq+=arr.get(i).freq;
            if(curr_freq >= n) break;            
        }
        k++;
        Node root  = new Node("A" + k, total_freq);
        ArrayList<Node> left_arr = new  ArrayList<Node>();
        ArrayList<Node> right_arr = new ArrayList<Node>();
        for(int m=0;m<arr.size();m++){
            if(m<=i) left_arr.add(arr.get(m));
            else right_arr.add(arr.get(m));
        }
        root.left = rec_fst((ArrayList<Node>)left_arr, k++);
        root.right = rec_fst((ArrayList<Node>)right_arr, k++);
        return root;


    }


    public static void printLevelOrder(Node root) 
    {   if(root == null) return;
        int h = height(root); 
        int i; 
        for (i=1; i<=h; i++) {
            printGivenLevel(root, i); 
            System.out.println();
        }
    } 

    public static int height(Node root) 
    { 
        if (root == null) 
           return 0; 
        else
        { 
            int lheight = height(root.left); 
            int rheight = height(root.right); 
              
            if (lheight > rheight) 
                return(lheight+1); 
            else return(rheight+1);  
        } 
    } 
  
    public static void printGivenLevel (Node root ,int level) 
    { 
        if (root == null) 
            return; 
        if (level == 1) 
            System.out.print("( " + root.value + " " + root.freq + " )"); 
        else if (level > 1) 
        { 
            printGivenLevel(root.left, level-1); 
            printGivenLevel(root.right, level-1); 
        } 
    }
    public static void get_encoding(Node root) throws IOException{
        if(root.left==null && root.right==null){
            mapping.put(root.value, "1");
        }
        else         encd_rec(root, "");

        FileWriter writer = new FileWriter("mapping.txt");
        Iterator it = mapping.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry p = (Map.Entry)it.next();
            System.out.println(p.getKey() + " -> " + p.getValue());
            writer.write("'" +p.getKey() + "'" + " -> " + p.getValue());
            writer.write("\r\n");
        }

        writer.close();
        FileWriter writer2 = new FileWriter("encoded.txt");
        String encoding = "";
        for(int i=0;i<mystring.length();i++){
            String enc = mapping.get(Character.toString(mystring.charAt(i)));
            encoding+=enc;
        }
        System.out.println("Encoded String is : " + encoding);
        encoded = encoding;
        writer2.write(encoding);
        writer2.write("\r\n");
        writer2.close();

    }

    


    public static void encd_rec(Node root, String enc) throws IOException{
        if(root == null) return;
        
        if(root.left==null && root.right==null){
            mapping.put(root.value, enc);
            
        }
            encd_rec(root.left, enc + "0");
            encd_rec(root.right, enc + "1");
        
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

class freq_comparator implements Comparator<Node>{
    public int compare(Node p, Node q){
        if(p.freq > q.freq) return 1;
        else return -1;
    }
}
