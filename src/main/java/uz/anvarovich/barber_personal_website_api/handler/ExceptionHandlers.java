package uz.anvarovich.barber_personal_website_api.handler;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.ApiResponse;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.AlreadyExist;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Objects;


@ControllerAdvice
public class ExceptionHandlers {
   /* @Value("${spring.profiles.active}")
    private String profileActive;*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        printStackTrace(ex);
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, false, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public void printStackTrace(Exception exs) {
        exs.printStackTrace();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalExceptions(Exception ex) {
        printStackTrace(ex);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Noma'lum xatolik: " + ex.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Token bilan bog'liq muammo", false, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.CONFLICT.value(), "Ma'lumotlar mos emas yoki allaqachon mavjud", false, null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "Sizda bu amalni bajarishga ruxsat yo'q", false, null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleNoSuchElementException(NoSuchElementException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Element topilmadi: " + e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AlreadyExist.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyExistException(AlreadyExist e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(EntityNotFoundException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Entity topilmadi: " + e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), false, null)
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException e) {
        printStackTrace(e);
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<String>> handleDisabled(DisabledException ex) {
        printStackTrace(ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiResponse<>(HttpStatus.FORBIDDEN.value(), ex.getMessage(), false, null)
        );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiResponse<String>> handleLocked(LockedException ex) {
        printStackTrace(ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiResponse<>(HttpStatus.FORBIDDEN.value(), ex.getMessage(), false, null)
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthError(AuthenticationCredentialsNotFoundException ex) {
        printStackTrace(ex);
        ApiResponse<Object> objectApiResponse = new ApiResponse<>(401, ex.getMessage(), false, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(objectApiResponse);
    }

    public record ErrorResponse(
            String message,
            Integer status,
            String errorCode
    ) {

    }
}
