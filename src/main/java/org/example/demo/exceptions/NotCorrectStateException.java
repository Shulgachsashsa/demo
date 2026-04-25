package org.example.demo.exceptions;

public class NotCorrectStateException extends RuntimeException {
  public NotCorrectStateException(String message) {
    super(message);
  }
}
