package prog.lab6.doplab.crypt;
import java.io.FileInputStream;
import java.io.IOException;

public class DecryptInputSream{
	short key;
	byte[] data;

	public DecryptInputSream(String name, short key){
		try (FileInputStream file = new FileInputStream(name)){
			data = new byte[file.available()];
			file.read(data);
        	file.close();
		} catch(IOException err){
			System.out.println(err.getMessage());
		}
		this.key = key;
	}
	public byte[] read(){
		return data;
	}
	public byte[] readDecrypt(){
		int len = data.length;
		byte[] tmp = new byte[len];
		for (int i = 0; i < len; i++) {
			tmp[i] = (byte)(key^data[i]);
		}
		return tmp;
	}
}