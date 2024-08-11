package io.memoria.notifications.infra.config;

public record Config(SubscriptionDB subscriptionDB,
                     double percentage,
                     TextTransformerType textTransformerType,
                     HttpServerConfig httpServerConfig) {}
