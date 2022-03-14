package iv1201.group7.recruitmentApp.domain;

/**
 * @author Group 7
 * 
 * This is the exception class for accepting or rejecting applications.
 * This is thrown if a recruiter already changed the status (accepted or rejected
 * the application) and another recruiter is trying to change it "at
 * the same time".
 */
public class HandleApplicationException extends Exception{
     
    /**
     * Creates an instance of the exception with an explanation message.
     * 
     * @param msg The message.
     */
    public HandleApplicationException(String msg){
        super(msg);
    }
}
    
