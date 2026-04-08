# 1. Base Debian imposée par le sujet
FROM debian:latest

# 2. Installation de Samba (Service de partage de fichiers)
RUN apt-get update && apt-get install -y \
    samba \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# 3. Création de l'utilisateur 'etudiant' pour le partage
RUN useradd -m etudiant && \
    (echo "pass123"; echo "pass123") | smbpasswd -a -s etudiant

# 4. Création du répertoire de partage
RUN mkdir -p /home/etudiant/partage && \
    chown -R etudiant:etudiant /home/etudiant/partage

# 5. Configuration simplifiée directement dans le Dockerfile
# On écrit le fichier de conf directement pour ne pas dépendre d'un fichier externe
RUN echo "[Partage-SAE]\n\
   path = /home/etudiant/partage\n\
   writable = yes\n\
   valid users = etudiant" > /etc/samba/smb.conf

# 6. Exposition du port Samba
EXPOSE 445

# 7. Lancement du service en mode premier plan
CMD ["smbd", "--foreground", "--no-process-group"]