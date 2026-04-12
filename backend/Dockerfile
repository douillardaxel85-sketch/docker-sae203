FROM debian:latest

RUN apt-get update && apt-get install -y \
	openjdk-17-jdk-headless \
	&& apt-get clean \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN mkdir ./partage && touch ./partage/bienvenue_SAE.txt

COPY ServeurDeFichiers.java .

RUN javac ServeurDeFichiers.java

EXPOSE 8080

CMD ["java", "ServeurDeFichiers"]