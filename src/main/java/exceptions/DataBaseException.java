package exceptions;

public class DataBaseException extends Exception{

    private static final String messageTemplate = "Exception in data base.\nOperation %s failed when working with %s table.";

    public DataBaseException(String operation, String tableName, Throwable cause) {
        super(String.format(messageTemplate, operation, tableName), cause);
    }
}
