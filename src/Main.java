public class Main {
    public static void main(String[] args) throws Exception {
       
        Tokenizer tokenizer = new Tokenizer("C:/Users/Developer/Desktop/Emergency/Tokenizer/src/test.txt");
        int token = tokenizer.getToken();
        while(token != 33 || token != -1){
            System.out.println(token);
            tokenizer.skipToken();
            token = tokenizer.getToken();
        }
    }
}
