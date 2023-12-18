# Build
FROM maven as build
WORKDIR /src
COPY src/main src/main
COPY pom.xml pom.xml

# Test
FROM build AS test
WORKDIR /src
COPY src/test src/test
COPY mnt /mnt
ENTRYPOINT [ "mvn", "test" ]

# Publish
FROM build as publish
WORKDIR /src
RUN mvn clean compile assembly:single

# Run
FROM amazoncorretto:11
WORKDIR /app
COPY --from=publish /src/target/LocalRestServer-1.0-SNAPSHOT-jar-with-dependencies.jar LocalRestServer.jar
CMD java -jar LocalRestServer.jar
EXPOSE 6491