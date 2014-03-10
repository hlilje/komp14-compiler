// ErrorMsg according to Appel book

public class AppelError {
    boolean anyErrors;
    void complain(String msg) {
        anyErrors = true;
        System.out.println(msg);
    }
}
