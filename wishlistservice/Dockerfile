FROM openjdk:8u92-jre-alpine
WORKDIR /wishlistservice
EXPOSE 8889
EXPOSE 27017
ADD ./build/libs/wishlistservice-0.0.1-SNAPSHOT.jar wishlistservice.jar
CMD java -jar wishlistservice.jar

