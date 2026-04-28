FROM debian:latest

RUN apt-get update && apt-get install -y default-jdk-headless

WORKDIR /app

COPY backend /app/backend

RUN javac backend/ServeurDeFichiers.java

RUN mkdir -p /app/partage

EXPOSE 8080

CMD ["java", "backend.ServeurDeFichiers"]