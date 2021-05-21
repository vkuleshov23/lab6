package prog.lab6.doplab;
import prog.lab6.doplab.crypt.*;
import java.nio.charset.Charset;

public class main{
	public static void main(String[] args){

		String inf = "new data for tests";
		byte item = (byte)' ';
		byte[] data = {(byte)'0', (byte)49, (byte)50, (byte)'3'};	
		short key = (short)((Math.random() * 1000) + (int)(Math.random()* 9) + 11);
		System.out.println("key: " + key +  "\n");

		EncryptOutputSream eos = new EncryptOutputSream(args[0], key);
		eos.write(inf.getBytes());
		eos.write(item);
		eos.write(data);
		eos.close();
		
		DecryptInputSream dis = new DecryptInputSream(args[0], key);
		Charset chSet = Charset.forName("UTF-8");
		String str1 = new String(dis.read(), chSet);
		String str2 = new String(dis.readDecrypt(), chSet);
		System.out.println("encoded information:\t" + str1 + "\ndecoded information:\t" +  str2);

	}
}