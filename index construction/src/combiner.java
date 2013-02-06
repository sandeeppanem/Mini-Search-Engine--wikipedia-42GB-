import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

public class combiner {
        static String word1 ;
        static String word2 ;
        static String value1 ;
        static String value2 ;
        public static void main(String[] args) {
                File folder = new File("/home/sandeep/Desktop/merge/");
                int count = 6480;//change this
                int c = 0;
                for(int i = c; folder.listFiles().length>1;i=i+2){
                        String filepath1="/home/sandeep/Desktop/merge/out"+i+".txt" ;
                        String filepath2="/home/sandeep/Desktop/merge/out"+(i+1)+".txt" ;
                        System.out.println(filepath1 +" "+filepath2);
                        try {
                                FileWriter ostream = new FileWriter("/home/sandeep/Desktop/merge/out"+count+".txt");
                                count++;
                                BufferedWriter out = new BufferedWriter(ostream);
                                BufferedReader br1 = new BufferedReader(new FileReader(filepath1),10*1024);
                                BufferedReader br2 = new BufferedReader(new FileReader(filepath2),10*1024);
                                String line1=null ;
                                String line2=null ;
                                line1 = br1.readLine();
                                line2 = br2.readLine();
                                while(true){
                                        setValues(line1,line2);
                                        if(word1.compareTo(word2) < 0){
                                                out.write(line1);
                                                out.newLine();
                                                line1=br1.readLine();
                                                if(line1==null)
                                                        break ;
                                        }
                                        if(word1.compareTo(word2) > 0){
                                                out.write(line2);
                                                out.newLine();
                                                line2=br2.readLine();
                                                if(line2==null)
                                                        break ;
                                        }

                                        if(word1.compareTo(word2) == 0){
                                                String line = line1+ " " + value2;
                                                out.write(line);
                                                out.newLine();
                                                line1=br1.readLine();
                                                line2=br2.readLine();
                                                if(line2==null || line1==null)
                                                        break ;
                                        }
                                }

                                if(line1==null){
                                        while(line2!=null){
                                                out.write(line2);
                                                out.newLine();
                                                line2=br2.readLine();
                                        }
                                }

                                if(line2==null){
                                        while(line1!=null){
                                                out.write(line1);
                                                out.newLine();
                                                line1=br1.readLine();
                                        }
                                }
                                br1.close();
                                br2.close();
                                out.close();
                                ostream.close();
                        }catch(IOException e){
                                e.printStackTrace();
                                System.exit(0) ;
                        }
                        new File(filepath1).delete();
                        new File(filepath2).delete();
                }
        }

        public static void createFile(String val){
                try {
                        FileWriter ostream = new FileWriter("Try.txt");
                        BufferedWriter out = new BufferedWriter(ostream);
                        out.write(val);
                        out.close();
                        ostream.close();
                }catch(IOException e){
                        e.printStackTrace();
                }
        }

        public static void setValues(String input1 ,String input2){
                word1=input1.substring(0,input1.indexOf(" "));
                value1=input1.substring(input1.indexOf(" ")+1);
                word2=input2.substring(0,input2.indexOf(" "));
                value2=input2.substring(input2.indexOf(" ")+1);
        }

        private static final Comparator<File> size = new Comparator<File>() {

                @Override
                public int compare(File o1, File o2) {
                        return o1.length() == o2.length() ? 0 : (o1.length() < o2.length() ? -1 : 1 ) ;
                }
        };
}