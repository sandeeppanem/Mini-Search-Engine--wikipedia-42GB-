import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;



public class tokenize {
	Stemmer s=new Stemmer();
	TreeMap<String,Hashtable<Integer,ArrayList<Short>>> h1=new TreeMap<String,Hashtable<Integer,ArrayList<Short>>>();
	Hashtable<Integer,ArrayList<Short>> h2;
	HashSet<String> stopwords=new HashSet<String>(1000,0.75f);
	ArrayList<Short> temp;
	BufferedWriter out1;
	String token,stemword,key,stop;
	StringTokenizer st;
	Integer id;
	short count;
	File file;
	FileWriter fstream;
	short i=0;
	public tokenize(String outpath)
	{

		try
		{
			String stopword="iam|coord|corner|corners|accepts|accepted|adjust|add|active|actual|accept|access|accessed|abduct|aas|abandoned|abolish|abound|length|less|lesser|abandon|live|low|local|log|logo|long|longer|longest|law|lead|learn|leave|led|left|elev|latino|launch|laura|limited|likewise|asof|line|link|aboutus|backed|est|utc|type|svg|settlement|see|blank|com|cite|well|zip|www|air|jpg|image|gni|map|longd|longm|longs|latd|latm|lats|latns|latew|age|aaa|aaaa|aaaaa|aaaaaa|maintain|alone|title|nbsp|able|about|above|according|accordingly|actually|afterwards|again|against|ahead|allow|ago|allows|across|after|aint|all|along|already|although|always|amid|amidst|amongst|another|anybody|anyhow|anyone|anything|anyway|anyways|anywhere|apart|almost|also|among|and|any|are|arent|around|aside|ask|asking|back|backwards|backward|became|become|becomes|beside|besides|beyond|both|brief|because|been|before|being|below|between|but|for|can|came|come|comes|cant|cannot|could|couldve|couldnt|dear|did|didnt|does|down|downwards|during|each|enough|especially|everybody|exactly|everyone|entirely|everything|doesnt|dont|either|else|ever|every|for|from|fairly|farther|few|found|further|get|got|gives|given|goes|gone|hardly|hence|here|hereafter|herein|instead|had|has|hasnt|have|hed|hell|hes|her|hers|him|himself|his|how|howd|howll|hows|however|id|ill|im|ive|into|isnt|its|its|inside|indicates|indeed|instead|inward|just|knows|known|least|let|like|likely|lately|later|latter|last|little|look|looks|looking|keep|keeps|kept|may|many|mainly|maybe|made|makes|make|merely|mine|miss|more|moreover|mean|meanwhile|mostly|much|need|near|nearly|needs|nobody|none|per|perhaps|placed|please|plus|possible|presumably|probably|provided|provides|quite|might|mightve|mightnt|most|must|mustve|mustnt|neither|nor|normally|nothing|now|not|off|often|only|outside|other|others|overall|otherwise|once|our|ought|over|ourselves|own|particular|particularly|past|rather|really|reasonably|recent|recently|regarding|regardless|relatively|respectively|round|saw|seem|seemed|seen|several|said|say|says|shall|shant|since|somebody|someday|somehow|someone|sure|something|therein|she|such|shed|shell|shes|still|specified|specifying|specify|should|shouldve|shouldnt|since|some|take|tell|tries|tried|tends|truly|taken|taking|than|that|thatll|thats|the|their|them|then|there|theres|those|these|they|theyd|theyll|theyre|thanks|theyve|this|tis|too|twas|till|unless|unto|until|up|upon|upwards|use|used|useful|uses|using|usually|various|went|whereas|under|unlike|unlikely|unfortunately|wants|was|wasnt|wed|were|werent|what|whatd|whats|when|when|whend|whenll|whens|where|whered|wherell|wheres|which|while|who|whod|wholl|whos|whom|why|whyd|whyll|whys|will|with|wont|would|wouldve|wouldnt|yet|you|youd|youll|youre|youve|your|yours|yourself|include|includes|including";

			st=new StringTokenizer(stopword,"|");
			while(st.hasMoreTokens())
			{
				stopwords.add(st.nextToken());

			};

			file = new File(outpath+"out.txt");
			fstream = new FileWriter(file.getAbsoluteFile());
			out1 = new BufferedWriter(fstream); 

		}
		catch(Exception e)
		{

		}
	}
	public void setTitle(String title,Integer id) {


		this.id=id;
		token=title.toLowerCase().replaceAll("[^\\x00-\\x7F]", "");
		st=new StringTokenizer(token," \n#@$%&^*(|=!_-,;");
		temp=new ArrayList<Short>();
		while(st.hasMoreTokens())
		{


			stemword= st.nextToken().replaceAll("[^a-zA-Z]+","");

			if(stemword.length()>2)
			{
				for(short i=0;i<5;i++)
				{
					if(i==0)
					{
						temp.add(i,(short)1);
					}
					else
					{
						temp.add(i,(short)0);
					}
				}

				h2=new Hashtable<Integer,ArrayList<Short>>(1000,0.75f);
				
				h2.put(id,new ArrayList<Short>(temp));


				h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));

				
				temp.clear();
				h2.clear();

			} 
		}

	}
	public void setText(String text)
	{
		key=(text.toString().toLowerCase()).replaceAll("[^\\x00-\\x7F]", "");
		st = new StringTokenizer(key," \n{}#@+$%&^*//(|=!_-;.)[]\",:?<>");
		temp=new ArrayList<Short>();
		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z]+","");			
			if(stopwords.contains(stemword))
			{
				continue;
			}

			if(stemword.length()>2)
			{
				s.add(stemword.toCharArray(),stemword.length());
				s.stem();
				stemword=s.toString();
				h2=h1.get(stemword);

				if(h2==null)
				{
					h2=new Hashtable<Integer,ArrayList<Short>>(1000,0.75f);
					for(short i=0;i<5;i++)
					{
						if(i==1)
						{
							temp.add(i,(short)1);
						}
						else
						{
							temp.add(i,(short)0);
						}
					}
					
					h2.put(id,new ArrayList<Short>(temp));
					h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));

					
					temp.clear();
					h2.clear();
				}
				else
				{
					if(h2.containsKey(id))
					{
						temp=h2.get(id);
						count=temp.get((short)1);
						if(count!=0)
						{
							temp.set(1,++count);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));

						}
						else
						{
							temp.set(1,(short)1);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));

						}
						temp.clear();
						h2.clear();
					}
					else
					{
						for(short i=0;i<5;i++)
						{
							if(i==1)
							{
								temp.add(i,(short)1);
							}
							else
							{
								temp.add(i,(short)0);
							}
						}
						h2.put(id,new ArrayList<Short>(temp));
						h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					}
					temp.clear();
					h2.clear();

				}
			}
		}
	}
	public void setInfobox(String infobox)
	{ 
		temp=new ArrayList<Short>();
		key=(infobox.toString().toLowerCase()).replaceAll("[^\\x00-\\x7F]", "");
		st = new StringTokenizer(key," \n#+//@$%&^*(|=!_-;.){}[]\",:?<>");
		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z]+","");			
			if(stopwords.contains(stemword))
			{
				continue;
			}

			if(stemword.length()>2)
			{
				s.add(stemword.toCharArray(),stemword.length());
				s.stem();
				stemword=s.toString();
				h2=h1.get(stemword);

				if(h2==null)
				{
					h2=new Hashtable<Integer,ArrayList<Short>>(1000,0.75f);
					for(short i=0;i<5;i++)
					{
						if(i==2)
						{
							temp.add(i,(short)1);
						}
						else
						{
							temp.add(i,(short)0);
						}
					}
					
					h2.put(id,new ArrayList<Short>(temp));
					h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					
					temp.clear();
					h2.clear();
				}
				else
				{

					if(h2.containsKey(id))
					{
						temp=h2.get(id);
						count=temp.get(2);


						if(count!=0)
						{

							temp.set(2,++count);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));


						}
						else
						{
							temp.set(2,(short)1);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
						}
						temp.clear();
						h2.clear();
					}
					else
					{
						for(short i=0;i<5;i++)
						{
							if(i==2)
							{
								temp.add(i,(short)1);
							}
							else
							{
								temp.add(i,(short)0);
							}
						}
						h2.put(id,new ArrayList<Short>(temp));
						h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					}
					temp.clear();
					h2.clear();

				}
			}
		}
	}



	public void setCategories(String category)
	{

		key=(category.toString().toLowerCase()).replaceAll("[^\\x00-\\x7F]", "");
		st = new StringTokenizer(key," \n#@$%&^*//+(|=!_-;.){}[]\",:?<>");
		temp=new ArrayList<Short>();
		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z]+","");			
			if(stopwords.contains(stemword))
			{
				continue;
			}

			if(stemword.length()>2)
			{
				s.add(stemword.toCharArray(),stemword.length());
				s.stem();
				stemword=s.toString();
				h2=h1.get(stemword);

				if(h2==null)
				{
					h2=new Hashtable<Integer,ArrayList<Short>>(1000,0.75f);
					for(short i=0;i<5;i++)
					{
						if(i==3)
						{
							temp.add(i,(short)1);
						}
						else
						{
							temp.add(i,(short)0);
						}
					}
					h2.put(id,new ArrayList<Short>(temp));
					h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					h2.clear();
					temp.clear();
				}
				else
				{
					if(h2.containsKey(id))
					{

						temp=h2.get(id);
						count=temp.get(3);
						if(count!=0)
						{
							temp.set(3,++count);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
						}
						else
						{
							temp.set(3,(short)1);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
						}
						temp.clear();
						h2.clear();
					}
					else
					{
						for(short i=0;i<5;i++)
						{
							if(i==3)
							{
								temp.add(i,(short)1);
							}
							else
							{
								temp.add(i,(short)0);
							}
						}
						h2.put(id,new ArrayList<Short>(temp));
						h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					}
					temp.clear();
					h2.clear();

				}
			}
		}
	}



	public void setLinks(String links)
	{
		key=(links.toString().toLowerCase());
		st = new StringTokenizer(key," \n//#+@$%&^*(|=!_-;.){}[]\",:?<>");
		temp=new ArrayList<Short>();
		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z]+","");			
			if(stopwords.contains(stemword))
			{
				continue;
			}

			if(stemword.length()>2)
			{
				s.add(stemword.toCharArray(),stemword.length());
				s.stem();
				stemword=s.toString();
				h2=h1.get(stemword);

				if(h2==null)
				{
					h2=new Hashtable<Integer,ArrayList<Short>>(1000,0.75f);
					for(int i=0;i<5;i++)
					{
						if(i==4)
						{
							temp.add(i,(short)1);
						}
						else
						{
							temp.add(i,(short)0);
						}
					}
					h2.put(id,new ArrayList<Short>(temp));
					h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					
					temp.clear();
					h2.clear();
				}
				else
				{
					if(h2.containsKey(id))
					{
						temp=h2.get(id);
						count=temp.get(4);
						if(count!=0)
						{
							temp.set(4,++count);

							h2.put(id,new ArrayList<Short>(temp));

							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));

						}
						else
						{
							temp.set(4,(short)1);
							h2.put(id,new ArrayList<Short>(temp));
							h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
						}
						temp.clear();
						h2.clear();
					}
					else
					{
						for(short i=0;i<5;i++)
						{
							if(i==4)
							{
								temp.add(i,(short)1);
							}
							else
							{
								temp.add(i,(short)0);
							}
						}
						h2.put(id,new ArrayList<Short>(temp));
						h1.put(stemword,new Hashtable<Integer,ArrayList<Short>>(h2));
					}
					temp.clear();
					h2.clear();

				}
			}
		}
	}



	public void print(String outpath)
	{

		try {

			Set<String> s1= h1.keySet();
			Iterator<String> it=s1.iterator();
			String key;
			Integer key1;
			Hashtable<Integer,ArrayList<Short>> value;
			StringBuffer print=new StringBuffer();
			String binary="";
			short decimal;
			short i1,i2,i3,i4,i5;
			short count;
			ArrayList<Short> value1;
			while(it.hasNext())
			{
				
				key=it.next().toString();
				print.append(key);
				value=h1.get(key);
				Set<Integer> s2= value.keySet();
				Iterator<Integer> it2=s2.iterator();
				while(it2.hasNext())
				{
					key1=it2.next();
					value1=(ArrayList<Short>)value.get(key1);
					i1=value1.get(0);


					i2=value1.get(1);

					i3=value1.get(2);

					i4=value1.get(3);
					i5=value1.get(4);
					count=(short)(i1+i2+i3+i4+i5);
					if(i3>0)
						i3=1;
					if(i4>0)
						i4=1;

					if(i2>0)
						i2=1;
					if(i1>0)
						i1=1;
					if(i5>0)
						i5=1;

					binary=i1+""+i2+""+i3+""+i4+""+i5;
                    try
                    {
					decimal=Short.parseShort(binary,2);
                    }catch(Exception e)
                    {
                    	decimal=8;
                    }
                    
					print.append(" "+key1+" "+decimal+" "+count);

				}

				out1.write(print+"\n");
				print.setLength(0);
			}
			h1.clear();
			out1.flush();
			out1.close();
			
			++i;
			file = new File(outpath+"out"+i+".txt");
			fstream = new FileWriter(file.getAbsoluteFile());
			out1 = new BufferedWriter(fstream);     
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
