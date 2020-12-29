From openjdk:8
copy ./target/movie-portal-0.0.1-SNAPSHOT.jar movie-portal-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","movie-portal-0.0.1-SNAPSHOT.jar"]

