package hello;

import hello.entity.Example;
import hello.exception.MyException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.SQLException;

@RestController
public class HelloController {

    @RequestMapping(value = "iae")
    public String helloIAE(){

        return throwIAE();
    }

    @RequestMapping(value = "sqle")
    public String helloSQLE() throws SQLException {

       return throwSQLE();
    }

    @RequestMapping(value = "ex")
    public String helloEx() throws Exception {
        return throwEx();
    }

    @RequestMapping("myEx")
    public String helloMyEx() throws MyException {
        return throwMyEx();
    }

    @RequestMapping(value = "valid", method = RequestMethod.POST)
    public Example validate(@Valid @RequestBody Example example, BindingResult result){

        if (result.hasErrors()){
            System.out.println(result.getFieldError().toString());
        }
        System.out.println(example.getFieldA());
        System.out.println(example.getFieldB());

        return example;
    }

    private String throwMyEx() throws MyException {
        throw new MyException("custom exception");
    }

    private String throwEx() throws Exception {
        throw new Exception("Exception throwed");
    }

    private String throwIAE() {
        throw new IllegalArgumentException("IllegalArgumentException throwed");
    }

    private String throwSQLE() throws SQLException {
        throw new SQLException("SQLException throwed");
    }

    /*@ResponseStatus(value = HttpStatus.BAD_GATEWAY, reason = "Плохой шлюз")
    @ExceptionHandler(IllegalArgumentException.class)
    public void argException(){

    }

    @ResponseBody
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String handleDataBaseException(){
        return "DB Error";
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ModelAndView handleExceptio(HttpServletRequest req, Exception ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }*/

}
