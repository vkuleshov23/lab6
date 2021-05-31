package prog.lab6.finput;
import java.io.IOException;
import prog.lab6.excpt.*;
public class FormattedInput{

	private static boolean ERR_FLAG = false;
	private static final boolean ERROR = true;
	private static final char END = '.';

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
			if(item_.length() == 0) return false;
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
			if(item_.length() == 0) return false;
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
					if(i+1 != format.length()){
						counter++;
					} else {
						FormattedInputException e = 
			 			new FormattedInputException("Incorrect specification format: " + format + '\n'); 
			 			throw e;
					}
				}
			}
			return counter;
		}
		public char nextFormat(){
			curFormatInd = format.indexOf('%', curFormatInd);
			if(curFormatInd != -1){
				if(curFormatInd+1 < format.length()){
					if(curFormatInd+2 < format.length()){ //checking for the correctness of the variable type interpreter
						if(!((format.charAt(curFormatInd+2) == ' ') || (format.charAt(curFormatInd+2) == '\t') || (format.charAt(curFormatInd+2) == '%'))){
							FormattedInputException e = 
			 				new FormattedInputException("Incorrect specification format: " + format + '\n'); 
			 				throw e;
			 			}
					}
					curFormatInd++;
				} else {
					return END;
				}
				return format.charAt(curFormatInd);
			} else {
				return END;
			}
		}
		public Object formatedElement(char format_, String item_){
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
				case '%':
					FormattedInputException e = 
			 		new FormattedInputException("Incorrect specification format: " + format + '\n'); 
			 		throw e;
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
			if(form == END){
				break;
			} else {
				if(item.data[i] != null){
					result[i] = take.formatedElement(form, item.data[i]);
				}
			}
		} 
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
			if(form == END){
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
			if(ERR_FLAG != ERROR)
				System.out.print("Please, input data: ");
			else
				System.out.print("[Invalid data] The data is differ from the specification: " + format + 
								"\nTry again: ");			
			ERR_FLAG = !ERROR;
			result =  tryScanf(format);			
		} while(ERR_FLAG == ERROR);
		return result;
	}
	public synchronized static final Object[] sscanf(String format, String in){
		Object[] result;
		ERR_FLAG = !ERROR;
		result =  trySScanf(format, in);			
		if(ERR_FLAG == ERROR) {	ERR_FLAG = false;
		 						FormattedInputException e = 
		 						new FormattedInputException("Sscanf...\nThe data is differ from the specification\nIvalid data"); 
		 						throw e; }
		return result;
	}
}