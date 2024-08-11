FROM memoriaio/java-docker:22.0.0

ARG APPLICATION_VERSION=0.0.1
COPY ./app/target/app-${APPLICATION_VERSION}.jar app.jar
COPY ./app/target/lib/ lib/

CMD ["java", "-jar", "--enable-preview", "app.jar"]