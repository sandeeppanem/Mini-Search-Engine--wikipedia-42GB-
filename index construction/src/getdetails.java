import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getdetails{
	int endpos=0;
	Pattern pattern = Pattern.compile("\\[\\[Category:(.)*\\]\\]\\n");
	Pattern pattern1 = Pattern.compile("\\[\\[(?!(Category))(.*?)\\]\\]", Pattern.MULTILINE);
	
	public void setendpos(int endpos)
	{
		this.endpos=endpos;
	}
	public int getendpos()
	{
		return endpos;
	}

public String parseInfoBox(String wikiText) {
	String INFOBOX_CONST_STR = "{{Infobox";
    int startPos = wikiText.indexOf(INFOBOX_CONST_STR);
    if(startPos < 0) return null;
    int bracketCount = 2;
    int endPos = startPos + INFOBOX_CONST_STR.length();
    for(; endPos < wikiText.length(); endPos++) {
      switch(wikiText.charAt(endPos)) {
        case '}':
          bracketCount--;
          break;
        case '{':
          bracketCount++;
          break;
        default:
      }
      if(bracketCount == 0) break;
    }
    if(endPos+1 >= wikiText.length()) return null;
    else setendpos(endPos+1);
    // This happens due to malformed Infoboxes in wiki text. See Issue #10
    // Giving up parsing is the easier thing to do.
    String  infoBoxText = wikiText.substring(startPos, endPos+1);
    infoBoxText = stripCite(infoBoxText); // strip clumsy {{cite}} tags
    // strip any html formatting
    infoBoxText = infoBoxText.replaceAll("&gt;", ">");
    infoBoxText = infoBoxText.replaceAll("&lt;", "<");
    infoBoxText = infoBoxText.replaceAll("<ref.*?>.*?</ref>", " ");
		infoBoxText = infoBoxText.replaceAll("</?.*?>", " ");
    
		return infoBoxText;
  }

  private String stripCite(String text) {
	  String CITE_CONST_STR = "{{cite";
    int startPos = text.indexOf(CITE_CONST_STR);
    if(startPos < 0) return text;
    int bracketCount = 2;
    int endPos = startPos + CITE_CONST_STR.length();
    for(; endPos < text.length(); endPos++) {
      switch(text.charAt(endPos)) {
        case '}':
          bracketCount--;
          break;
        case '{':
          bracketCount++;
          break;
        default:
      }
      if(bracketCount == 0) break;
    }
    text = text.substring(0, startPos-1) + text.substring(endPos);
    return text;   
  }
  public String getCategories(String text){
	  String categories="";
	  
	  Matcher matcher = pattern.matcher(text);
	  while (matcher.find()) {
	      categories = categories+" "+matcher.group().replaceAll("\\[\\[Category:","");
	    }
	  return categories;
  }
  
  public String getLinks(String text){
	  String links="";
	  
	  Matcher matcher = pattern1.matcher(text);
	  while (matcher.find()) {
	      links = links+" "+matcher.group();
	    }
	  return links;
  }
  
  
}