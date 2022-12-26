From openjdk:11
copy ./target/springboot2-jpa-crud-example-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]