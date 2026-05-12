package com.chatop.chatopapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

    /** * Entité Message représentant un message entre utilisateurs */
    @Entity
    @Table(name = "messages")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Message {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**     * Liaison ManyToOne vers User (expéditeur)     * Un User peut envoyer plusieurs Messages     * Foreign Key : user_id     */
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        /**     * Liaison ManyToOne vers Rental     * Une Rental peut recevoir plusieurs Messages     * Foreign Key : rental_id     */
        @ManyToOne
        @JoinColumn(name = "rental_id", nullable = false)
        private Rental rental;

        @Column(length = 2000)
        private String message;

        @CreationTimestamp
        @Column(nullable = false, updatable = false)
        private LocalDateTime created_at;

        @UpdateTimestamp
        @Column(nullable = false)
        private LocalDateTime updated_at;
}
