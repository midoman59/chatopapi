package com.chatop.chatopapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface GestionImageService {
    String enregistrerPhoto(MultipartFile fichier);
    String recupererUrlPhoto(String cheminPhoto);
}
