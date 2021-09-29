public class Main {
    public static void main(String[] args) throws Exception {
       
        Tokenizer tokenizer = new Tokenizer("C:/Christopher/School/OSU/AU21/CSE3341/Project/Tokenizer/src/test.txt");
        int token = 0;
        while((token = tokenizer.getToken()) != 33 || token != -1){
            System.out.println(token);
            tokenizer.skipToken();
        }
    }
}
