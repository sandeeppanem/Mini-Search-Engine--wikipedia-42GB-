

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class title extends DefaultHandler {
	StringBuffer tmpValue=new StringBuffer();
    String file;
    static File file1;
	static FileWriter fstream;
	static BufferedWriter out1;
	
    
    public title(String file) {
    	try
    	{
    		file1 = new File("/home/sandeep/Desktop/titles.txt");
    		fstream = new FileWriter(file1.getAbsoluteFile());
    		out1 = new BufferedWriter(fstream);  
    	}
    	catch(Exception e)
    	{
    		
    	}
    	this.file=file;
        parseDocument();
        
        
    }
    
    private void parseDocument() {
    	
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
    }
    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
    	
    	if (element.equalsIgnoreCase("title")) {
    		String title=tmpValue.toString().replaceAll("[^\\x00-\\x7F]", "");
    		tmpValue=new StringBuffer(title);
    		
    		for(int x=title.length();x<270;x++)
        	{
        		
    			tmpValue.append(" ");
        	}
        	//System.out.println(tmpValue.length());
    		try
    		{
    		if(out1!=null)
    		{
    			
    		out1.write(tmpValue.toString());
    		out1.write("\n");
            }}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
              
    	}
       
    }
        
        	
            
            
            
       
        
        
       
    
    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
    	tmpValue.append(ac,i,j);
    	
    	
    }
    public static void main(String[] args) {
       
new title(args[0]);
try
{
out1.flush();
out1.close();
}catch(Exception e)
{
	
}
    }
}
