mvn clean compile install
docker build -t anishnath/dockerfile:notemusic .
docker run -p 8084:8080 anishnath/dockerfile:notemusic
