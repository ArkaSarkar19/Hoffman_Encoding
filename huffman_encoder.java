import java.util.*;
import java.io.*;
class huffman_encoder{
    public static Map<String, String> mapping = new HashMap<String,String>();
    public static String mystring;
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
        try{
        ArrayList<Node> a = gen_nodes(s);

        a.forEach(x ->{
            System.out.println(x.value + " "  + x.freq);
        });
        System.out.println("-----------------Level order traversal-------------");
        Node root = gen_hoffman_tree(a);
        printLevelOrder(root);
        System.out.println("------------------------Encoding----------------------");
        get_encoding(root);
    }
    catch (NullPointerException e){

        System.out.println("----------------------------Input File is Empty----------------------------");
        try{
        File f1 = new File("mapping.txt");
        if(f1.exists())f1.delete();
        f1.createNewFile();
        File f2 = new File("encoded.txt");
        if(f2.exists())f2.delete();
        f2.createNewFile();
        }
        catch (IOException f) {};

    }


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

    public static Node gen_hoffman_tree(ArrayList<Node> node){
        
        PriorityQueue<Node> queue = new PriorityQueue<Node>(new value_comparator());
        node.forEach(x->{
            queue.add(x);
        });
        int k = 0;
        while(queue.size()!=1){
            k++;
            Node min1 = queue.poll();
            Node min2 = queue.poll();
            String s_n = "A" + k;
            Node add = new Node(s_n, min1.freq + min2.freq);
            add.left = (min1.freq > min2.freq) ? min1 : min2;
            add.right = (min1.freq > min2.freq) ? min2 : min1;
            queue.add(add);
        }
        return queue.poll();

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
    
    
    
}
class value_comparator implements Comparator<Node>{
    
    public int compare(Node p, Node q){
        if(p.freq > q.freq) return 1;
        else return -1;
    }
}
class Node{
    String value;
    int freq;
    Node left;
    Node right;
    public Node(String value, int freq){
        this.value = value;
        this.freq = freq;
        left = null;
        right = null;
    }
    public void display(){
        System.out.println(value + " " + freq);
    }
}