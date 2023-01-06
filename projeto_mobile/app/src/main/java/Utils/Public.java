package Utils;

public class Public {
    public static String IP = "10.0.2.2";
    public static String PORT = "8080";
    public static final String apiURL= String.format("http://%s:%s/api", IP, PORT);
    public static final String imgURL= String.format("http://%s:%s/img/", IP, PORT);
    public static final String SHARED_FILE="DADOS_USER";
    public static final String TOKEN = "TOKEN";
    public static final String PROMOCODE = "PROMOCODE";
}
