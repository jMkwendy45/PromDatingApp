package africa.semicolon.promiscuous.enums;

public enum ExceptionMessage {
    USER_NOT_FOUND_EXCEPTION("User not found"),
    USER_WITH_EMAIL_NOT_FOUND_EXCEPTION("User with email %s not found"),
    USER_REGISTRATION_FAILED_EXCEPTION("User registration failed"),
    ACCOUNT_ACTIVATION_FAILED_EXCEPTION("Account activation was not successful"),

    INVALID_CREDENTIAL_EXCEPTION("Invalid credential"),

     AUTHENTICATION_NOT_SUPPORT("aunthentication not supported");
    ExceptionMessage(String message){
        this.message = message;
    }
    private final String message;

    public String getMessage(){
        return message;
    }
}
