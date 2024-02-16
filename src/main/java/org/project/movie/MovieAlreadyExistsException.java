package org.project.movie;

public class MovieAlreadyExistsException extends RuntimeException {

	public MovieAlreadyExistsException (String message) {
		super(message);
	}

}
