FROM gradle:jdk17-alpine AS BUILD
WORKDIR /springCRUD_build
COPY . /springCRUD_build
RUN gradle bootJar

FROM eclipse-temurin:17-jre-alpine
ENV PORT=8080 DATABASE_PORT=5432 DATABASE_HOST=localhost
WORKDIR /spring_CRUD
COPY --from=BUILD /springCRUD_build/build/libs/*.jar /spring_CRUD/spring_CRUD.jar
EXPOSE 8080
CMD java -Dserver.port=$PORT -jar spring_CRUD.jar --spring.datasource.url=jdbc:mysql://$DATABASE_HOST:$DATABASE_PORT/bookDatabase