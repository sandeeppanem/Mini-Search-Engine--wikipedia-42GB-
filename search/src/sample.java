import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String buff;
		try
		{
		BufferedReader br1 = new BufferedReader(new FileReader("/home/sandeep/Desktop/merge/out12958.txt"),10*1024);
		 FileWriter ostream = new FileWriter("/home/sandeep/Desktop/merge/offset1.txt");
		 FileWriter ostream1 = new FileWriter("/home/sandeep/Desktop/merge/offset2.txt");
		 BufferedWriter out = new BufferedWriter(ostream);
		 BufferedWriter out1 = new BufferedWriter(ostream1);
		while((buff=br1.readLine())!=null)
				{
			String key=buff.substring(0,buff.indexOf(" "));
			String posting=buff.substring(buff.indexOf(" ")+1);
			out.write(key);
			out.write("\n");
			out1.write(posting);
			out1.write("\n");
				}
		out.close();
		out1.close();
		br1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
