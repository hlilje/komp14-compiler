/**
 * Class representing a compilation error.
 */

package error;

public class ErrorMsg {
    private ErrorHandler.ErrorCode errorCode;
    private String errorMessage;
    private boolean reported;

    public ErrorMsg(String message, ErrorHandler.ErrorCode code) {
        errorMessage = message;
        reported = false;
        errorCode = code;
    }

    public boolean isReported() {
        return reported;
    }

    public String toString() {
        reported = true;
        return errorMessage;
    }

    public ErrorHandler.ErrorCode getErrorCode() {
        return errorCode;
    }
}
