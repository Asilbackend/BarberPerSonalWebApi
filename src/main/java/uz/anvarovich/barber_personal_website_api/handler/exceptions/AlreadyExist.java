package uz.anvarovich.barber_personal_website_api.handler.exceptions;

public class AlreadyExist extends RuntimeException {
    public AlreadyExist(String message) {
        super(message);
    }
}
