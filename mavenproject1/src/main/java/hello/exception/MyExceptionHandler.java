package hello.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Component
public class MyExceptionHandler {
    private RequestResponseBodyMethodProcessor responseBodyProcessor;

    private ExceptionHandlingController exceptionHandler;

    /*@PostConstruct
    public void init() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(2);
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        responseBodyProcessor = new RequestResponseBodyMethodProcessor(converters);
    }*/

    public void handle(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException, HttpMediaTypeNotAcceptableException {
        handleReturnValue(exceptionHandler.handleCustomEcxeption(response, ex), request, response);
    }

    public void handleOldException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException, HttpMediaTypeNotAcceptableException {
        handleReturnValue(exceptionHandler.handleCustomEcxeption(response, ex), request, response);
    }

    private void handleReturnValue(Object returnValue, HttpServletRequest request, HttpServletResponse response)
            throws IOException, HttpMediaTypeNotAcceptableException {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        responseBodyProcessor.handleReturnValue(returnValue, null, new ModelAndViewContainer(), servletWebRequest);
    }

    public ExceptionHandlingController getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandlingController exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}
