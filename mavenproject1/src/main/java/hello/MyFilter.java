package hello;


import hello.entity.MyHeader;
import hello.exception.ExceptionHandlingController;
import hello.exception.MyException;
import hello.exception.MyExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MyFilter implements Filter {
    private static final String HEADER_NAME = "myHeader";
//    @Autowired
    private MyExceptionHandler exceptionHandlingController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //Enumeration headers = httpServletRequest.getHeaderNames();

        MyHeader myHeader = new MyHeader();
        myHeader.setMyHeader(httpServletRequest.getHeader(HEADER_NAME));
//        validateHeader(myHeader);

        try {
            validateHeaderMy(myHeader);
        } catch (MyException ex) {
            exceptionHandlingController.handle(ex, (HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void validateHeader(MyHeader header) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        validate(header, validator);

    }

    private void validate(Object object, Validator validator) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        System.out.println(object);
        System.out.println(String.format("Кол-во ошибок: %d", constraintViolations.size()));

        for (ConstraintViolation<Object> cv : constraintViolations)
            System.out.println(String.format("Внимание, ошибка! property: [%s], value: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
    }

    private void validateHeaderMy(MyHeader header) throws MyException {
        if (header.getMyHeader() == null) {
            throw new MyException(HEADER_NAME + " is Empty");
        } else {
            if (!isHeaderValueValid(header.getMyHeader())) {
                throw new MyException(HEADER_NAME + " is not valid");
            }
        }

    }

    private static boolean isHeaderValueValid(String headerValue) {
        /*Pattern p = Pattern.compile("^(@#@*)");
        Matcher m = p.matcher(headerValue);*/
        return headerValue.startsWith("@#@");
    }

    public MyExceptionHandler getExceptionHandlingController() {
        return exceptionHandlingController;
    }

    public void setExceptionHandlingController(MyExceptionHandler exceptionHandlingController) {
        this.exceptionHandlingController = exceptionHandlingController;
    }
}
