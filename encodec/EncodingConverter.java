package prog.lab6.encodec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.SortedMap;
import java.util.Iterator;
import java.io.IOException;
import java.nio.charset.Charset;
import prog.lab6.excpt.*;

public class EncodingConverter {
		
		private static boolean EncodingChecker(String from, String to){
			SortedMap<String,Charset> charsets = Charset.availableCharsets();
			Iterator iterator = charsets.keySet().iterator();
			int flag = 0;
			while(iterator.hasNext()) {
				String key   =(String) iterator.next();
				if(key.equalsIgnoreCase(from) || key.equalsIgnoreCase(to)) 
					flag++;
			}
			if(flag == 2) return true;
			return false;
		}

		public static final void converter(String in, String out, String from, String to) throws IOException {
			File fileIn = new File(in);
	        File fileOut = new File(out);

	        FileInputStream inputStream = new FileInputStream(fileIn);
	        FileOutputStream outStream  = new FileOutputStream(fileOut);


	        byte[] buffer = new byte[inputStream.available()];
	        inputStream.read(buffer);
	        inputStream.close();
	        if (EncodingChecker(from, to) == false){
	        	EncodingConverterException e = new EncodingConverterException("Ivalid encoding format"); throw e; 
	        }
	        Charset chSetFrom = Charset.forName(from);
	        Charset chSetTo = Charset.forName(to);

	        String string = new String (buffer, chSetTo);
	        
	        outStream.write(string.getBytes(chSetFrom));
	        outStream.close();
	    }
}
