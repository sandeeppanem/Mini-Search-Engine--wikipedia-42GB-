import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.TreeMap;




public class search {
	Stemmer stem=new Stemmer();	
	bsearch find =new bsearch();
   static  long startTime;
	static HashSet<String> stopwords=new HashSet<String>(1000,0.75f);
	String key,stop,stemword;
	StringTokenizer st;
	String[] queries=new String[10];
	static int querylength=0;
	static ArrayList<HashSet<String>> docids=new ArrayList<HashSet<String>>();;
	HashMap<String,Double> h2=new HashMap<String,Double>();
	TreeMap<Double,ArrayList<String>> s=new TreeMap<Double,ArrayList<String>>();
	TreeMap<Integer,Integer> sort=new TreeMap<Integer,Integer>();
	
	public search()
	{
		
			String stopword="iam|coord|corner|corners|accepts|accepted|adjust|add|active|actual|accept|access|accessed|abduct|aas|abandoned|abolish|abound|length|less|lesser|abandon|live|low|local|log|logo|long|longer|longest|law|lead|learn|leave|led|left|elev|latino|launch|laura|limited|likewise|asof|line|link|aboutus|backed|est|utc|type|svg|settlement|see|blank|com|cite|well|zip|www|air|jpg|image|gni|map|longd|longm|longs|latd|latm|lats|latns|latew|age|aaa|aaaa|aaaaa|aaaaaa|maintain|alone|title|nbsp|able|about|above|according|accordingly|actually|afterwards|again|against|ahead|allow|ago|allows|across|after|aint|all|along|already|although|always|amid|amidst|amongst|another|anybody|anyhow|anyone|anything|anyway|anyways|anywhere|apart|almost|also|among|and|any|are|arent|around|aside|ask|asking|back|backwards|backward|became|become|becomes|beside|besides|beyond|both|brief|because|been|before|being|below|between|but|for|can|came|come|comes|cant|cannot|could|couldve|couldnt|dear|did|didnt|does|down|downwards|during|each|enough|especially|everybody|exactly|everyone|entirely|everything|doesnt|dont|either|else|ever|every|for|from|fairly|farther|few|found|further|get|got|gives|given|goes|gone|hardly|hence|here|hereafter|herein|instead|had|has|hasnt|have|hed|hell|hes|her|hers|him|himself|his|how|howd|howll|hows|however|id|ill|im|ive|into|isnt|its|its|inside|indicates|indeed|instead|inward|just|knows|known|least|let|like|likely|lately|later|latter|last|little|look|looks|looking|keep|keeps|kept|may|many|mainly|maybe|made|makes|make|merely|mine|miss|more|moreover|mean|meanwhile|mostly|much|need|near|nearly|needs|nobody|none|per|perhaps|placed|please|plus|possible|presumably|probably|provided|provides|quite|might|mightve|mightnt|most|must|mustve|mustnt|neither|nor|normally|nothing|now|not|off|often|only|outside|other|others|overall|otherwise|once|our|ought|over|ourselves|own|particular|particularly|past|rather|really|reasonably|recent|recently|regarding|regardless|relatively|respectively|round|saw|seem|seemed|seen|several|said|say|says|shall|shant|since|somebody|someday|somehow|someone|sure|something|therein|she|such|shed|shell|shes|still|specified|specifying|specify|should|shouldve|shouldnt|since|some|take|tell|tries|tried|tends|truly|taken|taking|than|that|thatll|thats|the|their|them|then|there|theres|those|these|they|theyd|theyll|theyre|thanks|theyve|this|tis|too|twas|till|unless|unto|until|up|upon|upwards|use|used|useful|uses|using|usually|various|went|whereas|under|unlike|unlikely|unfortunately|wants|was|wasnt|wed|were|werent|what|whatd|whats|when|when|whend|whenll|whens|where|whered|wherell|wheres|which|while|who|whod|wholl|whos|whom|why|whyd|whyll|whys|will|with|wont|would|wouldve|wouldnt|yet|you|youd|youll|youre|youve|your|yours|yourself|include|includes|including";

			st=new StringTokenizer(stopword,"|");
			while(st.hasMoreTokens())
			{
				stopwords.add(st.nextToken());

			};
	
	}
	public void calculatetfidf2(String term,int k)
	{
		String ids=find.search(term);
		if(ids==null)
			System.out.println("word not found");
		if(ids!=null)
		{
			
			String[] docs=ids.split(" ");
			HashSet<String> h1=new HashSet<String>();
			String document="";
			Double value;
			Double value1;
			int sum=docs.length;
			if(sum>30000)
			{
				sum=30000+k*3;
				sort.put(sum,k);
			}
			else
				sort.put(sum+k,k);
			for(int r=0;r<sum;r=r+3)
			{
				document=docs[r];
				value=Double.parseDouble(docs[r+2]);
				Double temp=value*Math.log(1000000/sum);
				h1.add(document);
				value1=h2.get(document);
				if(value1==null)
					h2.put(document,temp);
				else
					h2.put(document,temp+value1);
			}
			docids.add(h1);
			
			
			if(k==(querylength-1))
			{
				
				int size=docids.size();
				Map.Entry<Integer,Integer> obj =sort.pollFirstEntry();
				Set<String> s1=docids.get(obj.getValue());
				//System.out.println(s1.size());
				for(int z=0;z<(size-1) && size>1;z++)
				{
					obj=sort.pollFirstEntry();
					Set<String> s2=docids.get(obj.getValue());
					s1.retainAll(s2);
					
				}
				if(s1.size()>=15)
				{
				Iterator<String> something=s1.iterator();
				while(something.hasNext())
				{
					String asalu=something.next().toString();
					Double racha=h2.get(asalu);
					if(s.containsKey(racha))
					{
						ArrayList<String> sample=s.get(racha);
						sample.add(asalu);
							s.put(racha,new ArrayList<String>(sample));
					}
					else
					{
					ArrayList<String> sample1=new ArrayList<String>();
					sample1.add(asalu);
						s.put(racha,new ArrayList<String>(sample1));
					
					}
				}
				}
				else
				{
					Set<String> s3=h2.keySet();
					Iterator<String> something=s3.iterator();
					while(something.hasNext())
					{
						String asalu=something.next();
						Double racha=h2.get(asalu);
						if(s.containsKey(racha))
						{
							ArrayList<String> sample=s.get(racha);
							sample.add(asalu);
								s.put(racha,new ArrayList<String>(sample));
						}
						else
						{
						ArrayList<String> sample1=new ArrayList<String>();
						sample1.add(asalu);
							s.put(racha,new ArrayList<String>(sample1));
						
						}
					}
				}
				try
				{
					
					RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Desktop/titles.txt","r");
					raf.seek(0);
					Set<Double> last=s.descendingKeySet();
					Iterator<Double> something1=last.iterator();
					int checkcheyy=0;
				while(something1.hasNext())
				{
					
					ArrayList<String> store=s.get(something1.next());
					for(int p=0;p<store.size();p++)
					{
						if(checkcheyy>14)
							break;
						checkcheyy++;
					long offset2=Long.parseLong(store.get(p).toString());
					if(offset2==1)
					{
						raf.seek(0);
					}
					else
					{
					offset2=(offset2-2)*271+271;
					raf.seek(offset2);
					System.out.println(raf.readLine());
					}
					}
					}
				raf.close();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			
			long endTime=System.currentTimeMillis();
			System.out.println("Took"+(endTime-startTime)+"ms");
			
				}
		}
	}
	public void calculateTfidf(String term,char query1,int k)
	{
		
		String ids=find.search(term);
		if(ids==null)
			System.out.println("word not found");
		
		if(ids!=null)
		{
		String[] docs=ids.split(" ");
		HashSet<String> h1=new HashSet<String>();
		String document="";
		Double value;
		Double value1;
		int sum=docs.length;
		
		if(sum>30000)
			sum=30000+k*3;
		switch(query1)
		{
		case 'T':
			//System.out.println(sum);
			for(int r=0;r<sum;r=r+3)
				{
				if((Integer.parseInt(docs[r+1]) & 16)!=0)
				{
					document=docs[r];
					value=Double.parseDouble(docs[r+2]);
					
					Double temp=10*value*Math.log(1000000/sum);
					
					h1.add(document);
					value1=h2.get(document);
					if(value1==null)
						h2.put(document,temp);
					else
						h2.put(document,temp+value1);
				}
				
				}
			docids.add(h1);
			sort.put(sum+k,k);
			
			break;
			
		case 'B':
			for(int r=0;r<sum;r=r+3)
			{
			if((Integer.parseInt(docs[r+1]) & 8)!=0)
			{
				document=docs[r];
				value=Double.parseDouble(docs[r+2]);
				Double temp=6*value*Math.log(1000000/sum);
				h1.add(document);
				value1=h2.get(document);
				if(value1==null)
					h2.put(document,temp);
				else
					h2.put(document,temp+value1);
				
			}
			
			}
			docids.add(h1);
			sort.put(sum+k,k);
			
			break;
		
		case 'I':
			for(int r=0;r<sum;r=r+3)
			{
			if((Integer.parseInt(docs[r+1]) & 4)!=0)
			{
				document=docs[r];
				value=Double.parseDouble(docs[r+2]);
				Double temp=7*value*Math.log(1000000/sum);
				h1.add(document);
				value1=h2.get(document);
				if(value1==null)
					h2.put(document,temp);
				else
					h2.put(document,temp+value1);
			}
			
			}
			docids.add(h1);
			sort.put(sum+k,k);
			
			break;
			
		case 'C':
			for(int r=0;r<sum;r=r+3)
			{
			if((Integer.parseInt(docs[r+1]) & 2)!=0)
			{
				document=docs[r];
				value=Double.parseDouble(docs[r+2]);
				Double temp=6*value*Math.log(1000000/sum);
				h1.add(document);
				value1=h2.get(document);
				if(value1==null)
					h2.put(document,temp);
				else
					h2.put(document,temp+value1);
			}
			
			}
			docids.add(h1);
			sort.put(sum+k,k);
			
			break;
		
		case 'O':
			for(int r=0;r<sum;r=r+3)
			{
			if((Integer.parseInt(docs[r+1]) & 1)!=0)
			{
				document=docs[r];
				value=Double.parseDouble(docs[r+2]);
				Double temp=6*value*Math.log(1000000/sum);
				h1.add(document);
				value1=h2.get(document);
				if(value1==null)
					h2.put(document,temp);
				else
					h2.put(document,temp+value1);
			}
			
			}
			docids.add(h1);	
			sort.put(sum+k,k);
		}
		if(k==(querylength-1))
		{
			
			int size=docids.size();
			Map.Entry<Integer,Integer> obj =sort.pollFirstEntry();
			Set<String> s1=docids.get(obj.getValue());
			//System.out.println(s1.size());
			for(int z=0;z<(size-1) && size>1;z++)
			{
				obj=sort.pollFirstEntry();
				Set<String> s2=docids.get(obj.getValue());
				s1.retainAll(s2);
				
			}
			if(s1.size()>=15)
			{
			Iterator<String> something=s1.iterator();
			while(something.hasNext())
			{
				String asalu=something.next().toString();
				Double racha=h2.get(asalu);
				if(s.containsKey(racha))
				{
					ArrayList<String> sample=s.get(racha);
					sample.add(asalu);
						s.put(racha,new ArrayList<String>(sample));
				}
				else
				{
				ArrayList<String> sample1=new ArrayList<String>();
				sample1.add(asalu);
					s.put(racha,new ArrayList<String>(sample1));
				
				}
			}
			}
			else
			{
				
				Set<String> s3=h2.keySet();
				Iterator<String> something=s3.iterator();
				while(something.hasNext())
				{
					String asalu=something.next();
					Double racha=h2.get(asalu);
					if(s.containsKey(racha))
					{
						ArrayList<String> sample=s.get(racha);
						sample.add(asalu);
							s.put(racha,new ArrayList<String>(sample));
					}
					else
					{
					ArrayList<String> sample1=new ArrayList<String>();
					sample1.add(asalu);
						s.put(racha,new ArrayList<String>(sample1));
					
					}
				}
			}
			//System.out.println(s);
			try
			{
				
				RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Desktop/titles.txt","r");
				raf.seek(0);
				Set<Double> last=s.descendingKeySet();
				Iterator<Double> something1=last.iterator();
				int checkcheyy=0;
			while(something1.hasNext())
			{
				
				
				ArrayList<String> store=s.get(something1.next());
				for(int p=0;p<store.size();p++)
				{
					if(checkcheyy>14)
						break;
					checkcheyy++;
				long offset2=Long.parseLong(store.get(p).toString());
				if(offset2==1)
				{
					raf.seek(0);
				}
				else
				{
				offset2=(offset2-2)*271+271;
				raf.seek(offset2);
				System.out.println(raf.readLine());
				}
				}
				}
			raf.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		
		long endTime=System.currentTimeMillis();
		System.out.println("Took"+(endTime-startTime)+"ms");
		}
		
		}
	}
	public void process_normal(String query,int k)
	{
		key=query.toLowerCase();
		st = new StringTokenizer(key," \n{}#@+$%&^*//(|=!_-;.)[]\",:?<>");
		int f=0;
		
		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z]+","");
			if(stopwords.contains(stemword))
			{
				continue;
			}
			if(stemword.length()>2)
			{
				stem.add(stemword.toCharArray(),stemword.length());
				stem.stem();
				stemword=stem.toString();
							
			++f;
			}
			}
		if(f==0)
			System.out.println("not found,enter another query");
		else
			calculatetfidf2(stemword,k);
	}
	public void  processQuery(String query,int k)
	{
		char query1=query.charAt(0);
		query=query.split(":")[1];
		key=query.toLowerCase();
		st = new StringTokenizer(key," \n{}#@+$%&^*//(|=!_-;.)[]\",:?<>");
		int f=0;
		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z]+","");
			if(stopwords.contains(stemword))
			{
				continue;
			}
			if(stemword.length()>2)
			{
				stem.add(stemword.toCharArray(),stemword.length());
				stem.stem();
				stemword=stem.toString();
							
			++f;
			}
			}
		if(f==0)
			System.out.println("not found,enter another query");
		
		else
		calculateTfidf(stemword,query1,k);
	}
	public static void main(String[] args){
		
		String query="";
		
		search s=new search();
		querylength=args.length;
		startTime=System.currentTimeMillis();
		//System.out.println(querylength);
		for(int i=0;i<args.length;i++)
		{
			query =args[i];
			
			if(query.contains(":"))
			s.processQuery(query,i);
			else
			{
				s.process_normal(query,i);
				
			}
		}
		
		
		

	}

}
