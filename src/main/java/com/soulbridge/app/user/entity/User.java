package com.soulbridge.app.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_email",
                        columnNames = "email"
                )
        }
)
public class User {

    @Id
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role = Role.USER;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "consent_version", length = 10, nullable = false)
    private String consentVersion = "v1.0";

    public enum Role {
        USER,
        ADMIN
    }

    public User(
            String username,
            String email,
            String password
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = true;
        this.role = Role.USER;
        this.consentVersion = "v1.0";
    }

    protected User() {
    }

    @PrePersist
    protected void onCreate() {

        if (createAt == null) {
            createAt = LocalDateTime.now();
        }

        if (consentVersion == null) {
            consentVersion = "v1.0";
        }

        if (active == null) {
            active = true;
        }

        if (role == null) {
            role = Role.USER;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getActive() {
        return active;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getConsentVersion() {
        return consentVersion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setConsentVersion(String consentVersion) {
        this.consentVersion = consentVersion;
    }
}