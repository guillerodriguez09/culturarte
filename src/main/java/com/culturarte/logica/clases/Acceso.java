package com.culturarte.logica.clases;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRO_ACCESO")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    private String url;
    private String browser;
    private String so;
    private LocalDateTime fecha;

    public Acceso() {}

    public Acceso(String ip, String url, String browser, String so, LocalDateTime fecha) {
        this.ip = ip;
        this.url = url;
        this.browser = browser;
        this.so = so;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }

    public String getSo() { return so; }
    public void setSo(String so) { this.so = so; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
