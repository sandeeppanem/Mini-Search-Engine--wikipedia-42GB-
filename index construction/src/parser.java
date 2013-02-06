
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class parser extends DefaultHandler {
	String file;
    StringBuffer tmpValue=new StringBuffer();
    StringBuffer tmpValue1;
    String infobox ="";
   static String outpath="";
    String send="";
    static tokenize t;
    static getdetails g;
   static combiner c;
    short flag=0;
short pagecount=0;
Integer id=0;
long startTime; 
long endTime;
    
    
    
    private void parseDocument(String file) {
    	
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        

       
        if (elementName.equalsIgnoreCase("title")) {
        	tmpValue.setLength(0);      
        	
        }
        if (elementName.equalsIgnoreCase("text")) {
        	tmpValue.setLength(0); 
        	
        }
        
        
        
    }
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
    	
    	if (element.equalsIgnoreCase("title")) {
    		++id;
    		t.setTitle(tmpValue.toString(),id);
           
    	}
       
       
        	
        if(element.equalsIgnoreCase("text")){
        	infobox=g.parseInfoBox(tmpValue.toString());
        	if(infobox!=null)
        	{
        	t.setInfobox(infobox);
        	}
        	int length=tmpValue.length();
        	int end=g.getendpos();
        	
        	
        	if(end<length)
        	{
        		send=tmpValue.toString().substring(end,length);
        	t.setCategories(g.getCategories(send));            	
        	t.setLinks(g.getLinks(send));
        	t.setText(send);
        	}
        
           
        }
        if(element.equalsIgnoreCase("page")){
        	flag=0;

        	++pagecount;
        	if(pagecount==2000)
        	{
        		t.print(outpath);
        		pagecount=0;
        	}
        	System.out.println(pagecount);
       	
                  }
        if(element.equalsIgnoreCase("file")){
        	t.print(outpath);
//System.out.println("merging files");
   	

//endTime=System.currentTimeMillis();
//System.out.println("Took"+(endTime-startTime)+"ms");
        	
            
            
            
         }
        
        }
       
   
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
    	tmpValue.append(ac,i,j);
    	//System.out.println(tmpValue);
    	
    }
    public static void main(String[] args) {
    	parser p=new parser();
    	
    	outpath=new String(args[1]);
    	t = new tokenize(outpath);
        g=new getdetails();
        c=new combiner();
    	p.parseDocument(args[0]);	
    	
    	
    
    

   
    //c.combine(outpath);
    }
}
