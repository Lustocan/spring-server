package it.example.lavoretti.exception;

public record GenericError(RestExceptionHandler.ErrorCodes status, String message) {

}
