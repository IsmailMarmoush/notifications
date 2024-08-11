package io.memoria.notifications.infra;

import io.memoria.atom.core.file.ConfigFileOps;
import io.memoria.atom.core.text.TextException;
import io.memoria.atom.jackson.JacksonTransformerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Utils {
  private static final Logger log = LoggerFactory.getLogger(Utils.class.getName());

  private Utils() {}

  public static <T> T readConfig(String configFile, Class<T> type) throws IOException, TextException {
    var configOps = new ConfigFileOps("#include:", true);
    var config = configOps.read(configFile);
    return JacksonTransformerBuilder.yaml().asTextTransformer().deserialize(config, type);
  }

  public static void printCurrentLogLevels(Logger log) {
    log.debug("DEBUG logging is on");
    log.info("INFO logging is on");
    log.warn("WARNING logging is on");
    log.error("ERROR logging is on");
  }

}
