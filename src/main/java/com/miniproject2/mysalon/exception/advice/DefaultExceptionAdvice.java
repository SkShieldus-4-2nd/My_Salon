package com.miniproject2.mysalon.exception.advice;


import com.miniproject2.mysalon.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(BusinessException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(ex.getHttpStatus().value());
        errorObject.setMessage(ex.getMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatusCode.valueOf(ex.getHttpStatus().value()));
    }


    //숫자타입의 값에 문자열타입의 값을 입력으로 받았을때 발생하는 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleException(HttpMessageNotReadableException e) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("message", e.getMessage());
        result.put("httpStatus", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorObject> handleException(RuntimeException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(e.getMessage());

        log.error(e.getMessage(), e);

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatusCode.valueOf(500));
    }

    //입력항목을 검증할때 발생하는 오류를 처리하는 메서드  BAD_REQUEST 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    //필드명
                    String fieldName = ((FieldError) error).getField();
                    //필드에러 메시지
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        ValidationErrorResponse response =
                new ValidationErrorResponse(
                        400,
                        "입력항목 검증 오류",
                        LocalDateTime.now(),
                        errors
                );
        //badRequest() 400
        return ResponseEntity.badRequest().body(response);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ValidationErrorResponse {
        //에러코드
        private int status;
        //에러메시지
        private String message;
        //에러발생한 시간
        private LocalDateTime timestamp;
        //개별항목에 에러정보
        private Map<String, String> errors;
    }
}
