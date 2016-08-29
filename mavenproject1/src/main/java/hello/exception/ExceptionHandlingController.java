package hello.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "Плохой шлюз")
    @ExceptionHandler(IllegalArgumentException.class)
    public void argException() {

    }

    @ResponseBody
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String handleDataBaseException() {
        return "DB Error";
    }

    /*@ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleExceptio(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav.toString();
    }*/


    @ResponseBody
    @ExceptionHandler(MyException.class)
    public String handleCustomEcxeption(HttpServletResponse response, Exception ex) {
        response.setStatus(230);
        return ex.getMessage();
    }

}
