package org.step.configuration.exception.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.step.configuration.exception.BadMessageException;
import org.step.configuration.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, BadMessageException.class})
    public String notFoundExceptionHandler(Exception e, Model model) {
        String message = e.getMessage();

        model.addAttribute("errorMessage", message);

        return "error";
    }
}
