package com.chatop.chatopapi.service.Impl;

import com.chatop.chatopapi.service.GestionImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class GestionImageServiceImpl implements GestionImageService {

    @Value("${app.images.dossier}")
    private String dossierImages;

    @Value("${app.base-url}")
    private String baseUrl;
    /**
     * @param fichier
     * @return
     */
    @Override
    public String enregistrerPhoto(MultipartFile fichier) {
        if (fichier == null || fichier.isEmpty()) {
            throw new IllegalArgumentException("Photo invalide");
        }

        try {
            String nomOriginal = fichier.getOriginalFilename();
            if (nomOriginal == null) {
                throw new IllegalArgumentException("Nom de fichier invalide");
            }
            nomOriginal = StringUtils.cleanPath(nomOriginal);
            String nomFichier = System.currentTimeMillis() + "_" + nomOriginal;

            Path dossier = Paths.get(dossierImages).toAbsolutePath().normalize();
            Files.createDirectories(dossier);

            Path destination = dossier.resolve(nomFichier);
            Files.copy(fichier.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Chemin stocké en base
            return "/uploads/" + nomFichier;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la photo", e);
        }
    }

    /**
     * @param cheminPhoto
     * @return
     */
    @Override
    public String recupererUrlPhoto(String cheminPhoto) {

        if (cheminPhoto == null || cheminPhoto.isBlank()) {
            return null;
        }
        if (cheminPhoto.startsWith("http://") || cheminPhoto.startsWith("https://")) {
            return cheminPhoto;
        }
        return baseUrl + cheminPhoto;
    }
}
