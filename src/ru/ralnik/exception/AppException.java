package ru.ralnik.exception;

import ru.ralnik.logging.Log;

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
		Log.error(message);
	}

	public AppException(String message) {
		super(message);
		Log.error(message);
	}

	public AppException(Throwable cause) {
		super(cause);
		Log.error(cause.getMessage());
	}
	
}
