package prog.lab6.doplab.crypt;
import java.io.FileOutputStream;
import java.io.IOException;

public class EncryptOutputSream{
	short key;
	FileOutputStream file;
	public EncryptOutputSream(String name, short key){
		try{
			this.file = new FileOutputStream(name);
			this.key = key;
		} catch(IOException err){
			System.out.println(err.getMessage());

		}
	}
	public void write(byte[] data){
		for(int i = 0; i < data.length; i++){
			data[i] = (byte)(key^data[i]); 
		}
		try{
			file.write(data);
		} catch(IOException err){
			System.out.println(err.getMessage());
		}
	}
	public void write(byte data){
		try{
			file.write((byte)(data^key));
		} catch(IOException err){
			System.out.println(err.getMessage());
		}
	}
	public void close(){
		try{
			file.close();
		} catch(IOException err){
			System.out.println(err.getMessage());
		}
	}
}