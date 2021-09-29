import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tokenizer {
    
    // Class variables 
    public int token;
    public PushbackReader pushbackReader;
    ArrayList<String> keywords = new ArrayList<>(Arrays.asList("program","begin", "end", "int", "if", "then", "else", "while", "loop", "read", "write"));
    ArrayList<String> specialCharacters = new ArrayList<String>(Arrays.asList(";",",","=","!","[","]","&&","||","(",")","+","-","*","!=","==","<",">","<=",">="));
    Map<String,Integer> grammarTable = new HashMap<String, Integer>();
    //--------------------------

    //Constructor
    Tokenizer(String fileName){
        for(int i =0; i<keywords.size();i++){
            this.grammarTable.put(keywords.get(i), i+1 );
        }
        // Add Special characters to grammar table
        for(int j =0; j < specialCharacters.size(); j++){
            this.grammarTable.put(specialCharacters.get(j), j+12 );
        }
        //---------
        // Remaining grammar items
        this.grammarTable.put("integer",31);
        this.grammarTable.put("identifier", 32);
        this.grammarTable.put("EOF", 33);
        try{
            this.pushbackReader = new PushbackReader(new FileReader(fileName)); 
        }catch(Exception e){
            System.out.println("ERROR USING BUFFER READER");
            e.printStackTrace();
        }
    }

    // Main methods
    public int getToken(){
        return this.token;
    }

    public void skipToken() throws IOException{
        int currentCharInt = this.pushbackReader.read();
        char currentChar = (char)currentCharInt;

        if(!Character.isWhitespace(currentChar)){
            StringBuilder builder = new StringBuilder();
            builder.append((char)currentCharInt);  


            if(this.specialCharacters.contains("" + currentChar)){ 
                if(currentChar =='&' || currentChar =='|' || currentChar=='<' || currentChar == '>' ||currentChar =='='){
                    char nextChar = (char) this.pushbackReader.read();
                    if((currentChar == '&' && nextChar == '&') || (currentChar == '|' && nextChar == '|') || (currentChar == '<' && nextChar =='=') || (currentChar == '>' && nextChar == '=') || (currentChar == '=' && nextChar == '=')){
                        builder.append(nextChar);
                        this.token = this.grammarTable.get(builder.toString());
                    }
                }else{
                    this.token = this.grammarTable.get(builder.toString());
                }
            }else if(Character.isLetter(currentChar)){
                char nextChar = (char) this.pushbackReader.read();
                while(Character.isLetterOrDigit(nextChar)){
                    builder.append(nextChar);
                }
            }
        }

    }

    public static int intVal(String token){
        return 1;
    }

    public static int idName(String token){
        return 1;
    }
}
