FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target/prodapp.jar prodapp.jar
CMD ["java", "-jar", "prodapp.jar"]