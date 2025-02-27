package de.caritas.cob.userservice.api.exception.httpresponses;

import java.io.Serial;

public class CreateEnquiryMessageException extends RuntimeException {

  @Serial private static final long serialVersionUID = -3553609955386498237L;

  public CreateEnquiryMessageException(String message) {
    super(message);
  }

  public CreateEnquiryMessageException(String message, Throwable exception) {
    super(message, exception);
  }
}
