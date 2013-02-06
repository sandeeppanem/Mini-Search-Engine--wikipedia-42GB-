import java.io.RandomAccessFile;



public class bsearch {
      
	
	public String search (String term) {
		String send=null;
		try
		{
		RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Desktop/merge/out12958.txt","r");
		RandomAccessFile raf1 = new RandomAccessFile("/home/sandeep/Desktop/offset.txt","r");
		 
		
		
		String buff="";
		int first=1,last,middle;
		boolean match=false;
		
		last=14065896;
		raf1.seek(0);
		
		while(first<=last && !match)
		{
			
			
			middle=first+(last-first)/2;
			if(middle==1)
				raf1.seek(0);
			else
			{
			long offset=(middle-1)*11;
			raf1.seek(offset);
			}
			buff=raf1.readLine();
			raf.seek(Long.parseLong(buff.trim()));
			buff=raf.readLine();
			
				String key=buff.substring(0,buff.indexOf(" "));
				String posting=buff.substring(buff.indexOf(" ")+1);
				int compare=key.compareTo(term);
				
				if(compare==0)
				{
					
					//System.out.println("match found");
					match=true;
					raf.close();
					raf1.close();
					return posting;
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
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return send;
	}

}
