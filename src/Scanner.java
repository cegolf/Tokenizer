import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Scanner {
    public ArrayList<Character> lineChars = new ArrayList<Character>();
    public Tokenizer tokenizer = new Tokenizer();
    Scanner(String fileName){
        String line = null;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while((line = bufferedReader.readLine()) != null){
                for(int i =0; i <line.length(); i++){
                    char c = line.charAt(i);
                    this.lineChars.add(c);
                }
            }
            tokenizer.tokenize(this.lineChars);
            bufferedReader.close();
        }catch(Exception e){
            System.out.println("ERROR USING BUFFER READER");
            e.printStackTrace();
        }
    }
}
