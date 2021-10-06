import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;


public class Tokenizer {
    
    // Class variables 
    public int token = 0;
    public int lastInt;
    public String identifier;
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
    public int getToken() throws IOException{
        if(this.token == 0){
            skipToken();
        }
        return this.token;
    }

    public void skipToken() throws IOException{
        int currentCharInt = this.pushbackReader.read();
        char currentChar = (char)currentCharInt;

        if(!Character.isWhitespace(currentChar)){
            
            StringBuilder builder = new StringBuilder();
            builder.append(currentChar);  

            if(this.specialCharacters.contains("" + currentChar)){ 
                ifSpecialChar(builder, currentChar);
            }else if(Character.isLetter(currentChar)){

                ifIsAlpha(builder);
                
            }else if(Character.isDigit(currentChar)){
                ifIsDigit(builder);

            }else if(currentCharInt == -1){
                this.token = 33;
            }
        }else if (currentCharInt == 13){
            ifIsWhitespace(currentCharInt, currentChar);
        }else if(currentCharInt == -1){
                this.token = 33;
        }else{
            if(currentCharInt == -1){
                this.token = 33;
            }else{
                skipToken();
            } 
        }

    }

    public int intVal(){
        return this.lastInt;
    }

    public String idName(){
        return this.identifier;
    }

    // Helper methods
    public void ifIsDigit(StringBuilder builder) throws IOException{
        int nextIntASCII = this.pushbackReader.read();
        char nextInt = (char) nextIntASCII;
        while(Character.isDigit(nextInt)){
            builder.append(nextInt);
            nextIntASCII = this.pushbackReader.read();
            nextInt = (char) nextIntASCII;
        }
        this.pushbackReader.unread(nextIntASCII);
        this.token = this.grammarTable.get("integer"); 
        this.lastInt = Integer.parseInt(builder.toString());
    }

    public void ifIsAlpha(StringBuilder builder) throws IOException{
        int nextCharInt = this.pushbackReader.read();
                char nextChar = (char) nextCharInt;

                while(Character.isLetterOrDigit(nextChar)){
                    builder.append(nextChar);
                    nextCharInt = this.pushbackReader.read();
                    nextChar = (char) nextCharInt;

                }
                // We have reached either whitespace or a special character so we need to unread the character
                this.pushbackReader.unread(nextCharInt);

                // Check if keywords contains the string that was built
                if(keywords.contains(builder.toString())){
                    this.token = this.grammarTable.get(builder.toString());
                }else{
                    String potentialToken = builder.toString();
                    if(potentialToken.length() <= 8){
                        if(potentialToken.matches("(([A-Z]+)([0-9]*))")){
                            this.token = this.grammarTable.get("identifier");
                            this.identifier = builder.toString();
                        }else{
                            this.token = -1;
                        } 
                    }else{
                        this.token = -1;
                    }
                }
    }

    public void ifIsWhitespace(int currentCharInt, char currentChar) throws IOException{
        while ((currentCharInt == 13 || currentCharInt == 9 || currentCharInt == 32 || currentCharInt == 10) && currentCharInt != -1){
            currentCharInt = this.pushbackReader.read();
            currentChar = (char) currentCharInt;
        }
        if(currentCharInt == -1){
            this.token = 33;
        }else{
            this.pushbackReader.unread(currentCharInt);
            skipToken();
        }
    }

    public void ifSpecialChar(StringBuilder builder, char currentChar) throws IOException{
        if(currentChar =='&' || currentChar =='|' || currentChar=='<' || currentChar == '>' ||currentChar =='=' || currentChar == '!'){
            int nextCharAscii = this.pushbackReader.read();
            char nextChar = (char) nextCharAscii;
            if((currentChar == '&' && nextChar == '&') || (currentChar == '|' && nextChar == '|') || (currentChar == '<' && nextChar =='=') || (currentChar == '>' && nextChar == '=') || (currentChar == '=' && nextChar == '=') || (currentChar == '!' && nextChar =='=')){
                builder.append(nextChar);
                this.token = this.grammarTable.get(builder.toString());
            }else{
                if(currentChar != '&' && currentChar != '|'){
                    this.pushbackReader.unread(nextCharAscii);
                    this.token = this.grammarTable.get(builder.toString());  
                }else{
                    this.token = -1;
                }
            }
        }else{
            this.token = this.grammarTable.get(builder.toString());
        }
    }
}
