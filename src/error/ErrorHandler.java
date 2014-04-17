/**
 * Error handler which stores and prints error messages.
 */

package error;

public class ErrorHandler {
    public static final boolean DEBUG = false;

    public static enum ErrorCode {
        NOT_FOUND, ALREADY_DEFINED, TYPE_MISMATCH, INTERNAL_ERROR,
        MISSING_MAIN, PARSE_ERROR, STATIC_THIS, OBJECT_PRINT,
        NON_ARRAY_LENGTH, WRONG_NUM_ARGS, UNDECLARED_TYPE
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
        for(int i=0; i<errors.size(); i++) {
            ErrorMsg e = errors.get(i);
            System.err.println("(" + (i + 1) + "/" + errors.size() + ") " + e.getErrorCode() + ": " + e);
        }
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

