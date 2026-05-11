package com.chatop.chatopapi.exception;

/**
 * Exception levée quand un email est déjà utilisé lors de l'inscription
 */
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
