package by.exchange.exception;

public class NoSuchEntityException extends RuntimeException {

  public static final String NO_FILIAL_MSG = "No filial exists with id '%d'";

  public NoSuchEntityException(String message) {
    super(message);
  }
}
