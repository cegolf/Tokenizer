public class Main {
    public static void main(String[] args) throws Exception {
       
        Tokenizer tokenizer = new Tokenizer(args[1]);
        int token = tokenizer.getToken();
        while(token != 33 && token != -1){
            System.out.println(token);
            tokenizer.skipToken();
            token = tokenizer.getToken();
        }
        if(token == -1){
            System.out.println("INVALID TOKEN");
        }else{
            System.out.println(token);
        }
    }
}
