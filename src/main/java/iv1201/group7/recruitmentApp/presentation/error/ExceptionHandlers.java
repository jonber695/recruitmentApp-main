package iv1201.group7.recruitmentApp.presentation.error;

import iv1201.group7.recruitmentApp.domain.HandleApplicationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Group 7
 * 
 * This class handles all errors.
 */
@Controller //Controller för att den ska kunna ta emot GetMapping till /failure
@ControllerAdvice
public class ExceptionHandlers implements ErrorController {
    
    @GetMapping("/failure")
        public String handleHttpError(HttpServletRequest request, HttpServletResponse response, Model model) {
       // LOGGER.debug("Http error handler got Http status: {}",
        request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String statusCode = extractHttpStatusCode(request);
        model.addAttribute("errorType", statusCode);
        response.setStatus(Integer.parseInt(statusCode));
        return "failure";
    }
        
        private String extractHttpStatusCode(HttpServletRequest request) {
        return request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
    }
        
        /**
         * Handles HandleApplicationException.
         * 
         * @param exception The HandleApplicationException.
         * @param model The model.
         * @return The search.html page.
         */
        @ExceptionHandler(HandleApplicationException.class) 
        @ResponseStatus(HttpStatus.OK)
        public String handleApplicationException(HandleApplicationException exception,
                Model model){
            if(exception.getMessage().contains("no application")){
            model.addAttribute("errorType", "missing-application");
            }
            if (exception.getMessage().contains("processed")){
                model.addAttribute("errorType", "handle-application");
            }
            if(exception.getMessage().contains("an application"))
                model.addAttribute("errorType", "existing-application");
            return "failure";
        }

        
     /**
     * Handles generic errors.
     *
     * @return The error page, showing a generic error text.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception, Model model) {
       // logExceptionErrorLevel(exception);
        model.addAttribute("errorType", "generic");
        return "failure";
    }
}
