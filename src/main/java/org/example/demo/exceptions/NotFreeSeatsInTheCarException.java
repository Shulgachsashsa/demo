package org.example.demo.exceptions;

public class NotFreeSeatsInTheCarException extends RuntimeException {
  public NotFreeSeatsInTheCarException(String message) {
    super(message);
  }
}
