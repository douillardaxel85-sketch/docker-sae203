FROM debian:latest

RUN apt-get update && apt-get install -y \
	default-jdk-headless \
	&& apt-get clean \
	&& rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY ServeurDeFichiers.java .

RUN javac ServeurDeFichiers.java && mkdir partage && touch partage/bienvenue_SAE.txt

EXPOSE 8080
CMD ["java", "ServeurDeFichiers"]