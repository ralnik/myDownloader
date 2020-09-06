package ru.ralnik.exception;

/**
 * 
 * @author ralnik
 * created: 04.09.2020
 * Главный exception приложения
 *
 */
public class AppException extends Exception {

	public AppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}
	
}
