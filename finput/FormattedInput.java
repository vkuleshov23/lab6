package prog.lab6.finput;
import java.io.IOException;
import prog.lab6.excpt.*;
public class FormattedInput{

	private static boolean ERR_FLAG = false;
	private static final boolean ERROR = true;

	private static class Input {
		
		private int code;
		private int len = 0;

		public String[] data;
		public int flag;
		
		Input(int len_){
			data = new String[len_];
			for (int i = 0; i < len_; i++) {
				data[i] = "";
			}
			len = len_;
			flag = 0;
		}
		private void flush(){
			if (flag == 1) return;

			try{
				while((char)(code = System.in.read()) != '\n');
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		public void input(){
			flag = 0;
			try {
				for (int i = 0; i < len; i++) {	
					while(((char)(code = System.in.read()) != '\n')){
						if(((char)code != '\t') && (code != 32)){
							break;
						}
					}
					if((char)code != '\n'){
						data[i] += (char)code;
					} else {
						flag = 1;
						return;
					}
					while(((char)(code = System.in.read()) != '\n')){
						if(((char)code == '\t') || ((char)code == ' ')){
							break;
						}
						data[i] += (char)code;
					}
					if((char)code == '\n'){
						flag = 1;
						return;
					}
					data[i].trim();
				}
				this.flush();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		public void input(String in){
			char c = 0;
			code = 0;
			int end = in.length()+1;
			for (int i = 0; i < len; i++) {
				while( (code+1) != end){
					c = in.charAt(code++);
					if((c != '\t') && (c != ' ') && (c != '\n') || (c == '\0')){
						break;
					}
				}
				if(c != '\0' || code == end){
					data[i] += c;
				} else {
					return;
				}
				while((code+1) != end ){
					c = in.charAt(code++);
					if((c == '\t') || (c == ' ') || (c == '\n') || (c == '\0')){
						break;
					}
					data[i] += c;
				}
				if(c == '\0' || code == end){
					return;
				}
				data[i].trim();
			}
		}
	}

	private static class Format {
		private int curFormatInd;
		private String format;
		Format(String format_){
			format = format_;
			curFormatInd = 0;
		}
		public boolean intChecker(String item_){
			for (int i = 0; i < item_.length(); i++) {
				char c = item_.charAt(i);
				if(!(((c >= '0') && (c <= '9')) || (c == '-'))){
					return false;
				}
				if(c == '-' && i != 0){
					return false;
				}
			}
			return true;
		}

		public boolean floatChecker(String item_){
			int pointCounter = 0;
			for (int i = 0; i < item_.length(); i++) {
				char c = item_.charAt(i);
				if(!(((c >= '0') && (c <= '9')) || (c == '-'))) {
					if(c != '.')
						return false;
					else
						pointCounter++;
				}
				if(c == '-' && i != 0){
					return false;
				}
			}
			return (pointCounter != 1) ? false : true;
		}
			
		public boolean charChecker(String item_){
			return (item_.length() != 1) ? false : true;
		}

		public int formatsCounter(){
			int counter = 0;
			for (int i = 0; i < format.length(); i++) {
				if(format.charAt(i) == '%'){
					counter++;
				}
			}
			return counter;
		}
		public char nextFormat(){
			curFormatInd = format.indexOf('%', curFormatInd);
			if(curFormatInd != -1){
				if(curFormatInd+1 <= format.length()){
					curFormatInd++;
				} else {
					return '.';
				}
				return format.charAt(curFormatInd);
			} else {
				return '.';
			}
		}
		public synchronized Object formatedElement(char format_, String item_){
			Object item = item_;
			switch(format_){
				case 'd':
					if(intChecker(item_)){
						item = Integer.parseInt(item_);
					} else {
						FormattedInput.ERR_FLAG = true;
					}
					break;
				case 'f':
					if(floatChecker(item_)){
						item = Float.parseFloat(item_);
					} else {
						FormattedInput.ERR_FLAG = true;
					}
					break;
				case 's':
					break;
				case 'c':
					if(charChecker(item_)){
						item = item_.charAt(0);
					} else {
						FormattedInput.ERR_FLAG = true;
					}
					break;
			}
			return item;
		}
	}
	private synchronized static final Object[] tryScanf(String format){
		format = format.trim();
		Format take = new Format(format);
		Object[] result = new Object[take.formatsCounter()];
		Input item = new Input(take.formatsCounter());
		item.input();
		for(int i = 0; ; i++){
			char form = take.nextFormat();
			if(form == '.'){
				break;
			} else {
				if(item.data[i] != null){
					result[i] = take.formatedElement(form, item.data[i]);
				}
			}
		} 
		return result;
	}
	public synchronized static final Object[] scanf(String format){
		Object[] result;
		do {
			ERR_FLAG = false;
			System.out.println("Please, input data: ");
			result =  tryScanf(format);			
		} while(ERR_FLAG == ERROR);
		return result;
	}

	private synchronized static final Object[] trySScanf(String format, String in){
		format = format.trim();
		in = in.trim();
		Format take = new Format(format);
		Object[] result = new Object[take.formatsCounter()];
		Input item = new Input(take.formatsCounter());
		item.input(in);
		for(int i = 0; ; i++){
			char form = take.nextFormat();
			if(form == '.'){
				break;
			} else {
				if(item.data[i] != null){
					result[i] = take.formatedElement(form, item.data[i]);
				}
			}
		} 
		return result;
	}
	public synchronized static final Object[] sscanf(String format, String in){
		Object[] result;
			ERR_FLAG = false;
			result =  trySScanf(format, in);			
			if(ERR_FLAG == ERROR) {	FormattedInputException e = new FormattedInputException("Sscanf...\nThe data is differ from the specification\nIvalid data"); throw e; }
		return result;
	}
}