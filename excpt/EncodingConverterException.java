package prog.lab6.excpt;
import java.nio.charset.Charset;
import java.util.SortedMap;
import java.util.Iterator;
public class EncodingConverterException extends RuntimeException {

    private String errorStr;

    public EncodingConverterException (String string) {
        this.errorStr = string;
        System.out.println("This is a EncodingConverterException...");
    }

    public void getMassage() {
        System.err.println("Error: " + errorStr);
    }
    public void getMSandSLT(){
        System.err.println("Error: " + errorStr);
    	if(errorStr == "Ivalid encoding format"){
    		SortedMap<String,Charset> charsets = Charset.availableCharsets();
			Iterator iterator = charsets.keySet().iterator();
			System.out.println("All possible formats: ");
			while(iterator.hasNext()) {
				String key   =(String) iterator.next();
				System.out.println(key);
			}
	    }
    }
}