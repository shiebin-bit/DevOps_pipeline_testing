FROM gradle:jdk17-alpine AS build
WORKDIR /springCRUD_build
COPY . /springCRUD_build
RUN chmod +x ./gradlew && ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
ENV PORT=8080 DATABASE_PORT=5432 DATABASE_HOST=localhost
WORKDIR /spring_CRUD
COPY --from=build /springCRUD_build/build/libs/*.jar /spring_CRUD/spring_CRUD.jar
EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar spring_CRUD.jar --spring.datasource.url=jdbc:mysql://$DATABASE_HOST:$DATABASE_PORT/bookDatabase"]
