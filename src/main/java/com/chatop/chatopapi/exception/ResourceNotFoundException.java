package com.chatop.chatopapi.exception;

/**
 * Exception levée quand une ressource (User, Rental, Message) n'est pas trouvée
 * Mappe vers le code HTTP 404 Not Found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

