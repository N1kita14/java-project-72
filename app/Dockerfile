FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./build/libs/app-1.0-SNAPSHOT-all.jar app.jar

ENV JAVA_OPTS="-Xmx512M -Xms512M"
EXPOSE 7070

CMD ["java", "-jar", "/app/app.jar"]