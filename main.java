package prog.lab6;
import java.io.IOException;
import prog.lab6.finput.*;
import prog.lab6.excpt.*;
public class main{
	public static void main(String[] args){			
		try{
			String format = "%s %f %d %c";
			String in = "GG -1.86 -23423 s";
			Object[] test1 = FormattedInput.sscanf(format, in);
			System.out.println("" + test1[0] + ", " + test1[1] + ", " + test1[2] + ", " + test1[3]);

			Object[] test2 = FormattedInput.scanf(format);
			// System.out.println("" + test2[0] + ", " + test2[1] + ", " + test2[2]);
			String t1 = (String)test2[0];
			float t2 = (float)test2[1];
			int t3 = (int)test2[2];
			char t4 = (char)test2[3];
			System.out.println("" + t1 + ", " + t2 + ", " + t3 + ", " + t4);

		} catch(FormattedInputException err){
			err.getMassage();
			err.printStackTrace();
		}
	}
}