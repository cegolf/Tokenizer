import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tokenizer {

    private final Set<String> keywords = new HashSet<>(Arrays.asList("program", "begin", "end", "new", "int", "define", "endfunc", "class", "extends", "endclass",
    "if", "then", "else", "while", "endwhile", "endif", "or", "input", "output", "ref"));

    public static String getToken(ArrayList<String> tokens){
        return "token";
    }

    public static void skipToken(){

    }

    public static int intVal(String token){
        return 1;
    }

    public static int idName(String token){
        return 1;
    }

    public void tokenize(ArrayList<Character> lineChars){
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<Character> token = new ArrayList<>();
        for(int i =0; i <lineChars.size();i++){
            char nextChar = lineChars.get(i);
            if(!Character.isWhitespace(nextChar)){
                token.add(nextChar);
            }
            else{
                StringBuilder builder = new StringBuilder();
                for(Character c : token){
                    builder.append(c);
                }
                token.clear();
                tokens.add(builder.toString());
            }
        }
        System.out.println(tokens);
    }
}
