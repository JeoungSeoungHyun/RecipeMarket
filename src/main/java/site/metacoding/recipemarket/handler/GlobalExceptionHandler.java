package site.metacoding.recipemarket.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import site.metacoding.recipemarket.handler.ex.CustomApiException;
import site.metacoding.recipemarket.handler.ex.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(Exception e) { // fetch 요청 시 발동 -> json 응답
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String htmlException(Exception e) { // 일반적인 Get(a태그), Post(form태그) 요청 -> html 응답
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + e.getMessage() + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
