import java.io.RandomAccessFile;




public class bsearch {
      
	
	public String search (String term) {
		String buff=null;
		try
		{
		RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Desktop/merge/words.txt","r");
		RandomAccessFile raf1 = new RandomAccessFile("/home/sandeep/Desktop/offset4.txt","r");
		RandomAccessFile raf2=new RandomAccessFile("/home/sandeep/Desktop/merge/postings.txt","r"); 
		RandomAccessFile raf3=new RandomAccessFile("/home/sandeep/Desktop/offset5.txt","r"); 
		
		
		int first=1,last,middle;
		boolean match=false;
		int offset=0;
		last=14065896;
		raf1.seek(0);
		while(first<=last && !match)
		{
			
			
			middle=first+(last-first)/2;
			if(middle==1)
				raf1.seek(0);
			else
			{
			offset=(middle-1)*11;
			raf1.seek(offset);
			
			}
			buff=raf1.readLine();
			Long move=Long.parseLong(buff.trim());
			raf.seek(move);
			buff=raf.readLine();
			
			int compare=buff.compareTo(term);
				
				if(compare==0)
				{
					
					raf3.seek(offset+11);
					buff=raf3.readLine();
					long move1=Long.parseLong(buff.trim());
					//System.out.println(move1);
					raf3.seek(offset);
					buff=raf3.readLine();
					move=Long.parseLong(buff.trim());
					byte[] store=new byte[(int)(move1-(move+1))];
					raf2.seek(move);
					//System.out.println("match found");
					raf2.readFully(store);
					match=true;
					
					raf.close();
					raf1.close();
					raf2.close();
					raf3.close();
		
					return new String(store);
				}else if(compare>0)
				{
					
					last=middle-1;
				
				}
				else
				{
					
				  					
					first=middle+1;
				}
			
		}
		if(!match)
		{
			System.out.println("not found");
		}
		
		raf.close();
		raf1.close();
		raf2.close();
		raf3.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return buff;
	}

}
