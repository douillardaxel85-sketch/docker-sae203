# 1. Base Debian
FROM debian:latest

# 2. Installation de Node.js (proprement sur Debian)
RUN apt-get update && apt-get install -y \
    curl \
    && curl -sL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && apt-get clean

# 3. Dossier de travail
WORKDIR /app

# 4. On copie les fichiers (même s'ils sont vides pour l'instant)
COPY package.json ./
COPY server.js ./

# 5. Port de l'app
EXPOSE 3000

# 6. Lancement
CMD ["node", "server.js"]