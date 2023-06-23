FROM --platform=linux/amd64 openjdk:17-jdk-alpine
EXPOSE 8081
ADD target/prodapp.jar prodapp.jar
CMD ["java", "-jar", "prodapp.jar"]