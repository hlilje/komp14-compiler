package error;

public class ErrorHandler {
    public static final boolean DEBUG = true;

    public static enum ErrorCode {
        NOT_FOUND, ALREADY_DEFINED, TYPE_ERROR
    }
    private boolean anyErrors;
    private java.util.ArrayList<ErrorMsg> errors;

    public ErrorHandler() {
        anyErrors = false;
        errors = new java.util.ArrayList<ErrorMsg>();
    }

    public void complain(String msg, ErrorCode code) {
        anyErrors = true;
        errors.add(new ErrorMsg(msg, code));

        if(DEBUG) System.err.println(msg);
    }

    public boolean anyErrors() {
        return anyErrors;
    }

    public void printErrors() {
        for(ErrorMsg e : errors)
            System.err.println(e.getErrorCode() + ": " + e);
    }

    public void clearReportedErrors() {
        java.util.Iterator<ErrorMsg> it = errors.iterator();
        while(it.hasNext()) {
            if(it.next().isReported()) {
                it.remove();
            }
        }
    }
}

