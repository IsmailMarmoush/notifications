package io.memoria.notifications.infra;

import io.helidon.http.media.jackson.JacksonRuntimeException;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.helidon.http.Status.BAD_REQUEST_400;

public class JsonErrorHandler implements io.helidon.webserver.http.ErrorHandler<JacksonRuntimeException> {
  private static final Logger log = LoggerFactory.getLogger(JsonErrorHandler.class.getName());

  @Override
  public void handle(ServerRequest request, ServerResponse response, JacksonRuntimeException exception) {
    response.status(BAD_REQUEST_400).send();
    log.warn(exception.getMessage(), exception);
  }
}
