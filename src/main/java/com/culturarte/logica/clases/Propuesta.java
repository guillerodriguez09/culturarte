package com.culturarte.logica.clases;

import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propuestas", uniqueConstraints = @UniqueConstraint(columnNames = "titulo"))
public class Propuesta {

    @Id
    private String titulo;

    private String descripcion;
    private String imagen; // opc
    private String lugar;
    private LocalDate fecha;
    private Integer precioEntrada;
    private Integer montoAReunir;
    private LocalDate fechaPublicacion;

  //una prop va a 1 cat
  @ManyToOne(optional = false)
  @JoinColumn(name = "CATEGORIA_NOMBRE", nullable = false)
  private Categoria categoria;

    @OneToOne
    private Estado estadoActual;

    @OneToMany(mappedBy = "propuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estado> historialEstados = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PROPONENTE_NICKNAME")  // fk
    private Proponente proponente;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "propuesta_retornos", joinColumns = @JoinColumn(name = "propuesta_titulo"))
    @Enumerated(EnumType.STRING)//se crea una tabla prop retornos con un campo q apunta a la propuesta
    @Column(name = "retorno", nullable = false)
    private List<ETipoRetorno> retornos = new ArrayList<>();

    // constructor vacio para jpa
    protected Propuesta() {}

    // constructor con param
    public Propuesta(Categoria categoria, Proponente proponente, String titulo,
                     String descripcion, String lugar, LocalDate fecha, Integer precioEntrada, Integer montoAReunir, LocalDate fechaPublicacion, List<ETipoRetorno> retornos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
        this.proponente = proponente;
        this.retornos = retornos != null ? new ArrayList<>(retornos) : new ArrayList<>();
        this.historialEstados = new ArrayList<>();
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getLugar() { return lugar; }
    public LocalDate getFecha() { return fecha; }
    public Integer getPrecioEntrada() { return precioEntrada; }
    public Integer getMontoAReunir() { return montoAReunir; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public Categoria getCategoria() { return categoria; }
    public Estado getEstadoActual() { return estadoActual; }
    public List<Estado> getHistorialEstados() { return historialEstados; }
    public Proponente getProponente() { return proponente; }
    public List<ETipoRetorno> getRetornos() { return retornos; }

    // Setters
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setPrecioEntrada(Integer precioEntrada) { this.precioEntrada = precioEntrada; }
    public void setMontoAReunir(Integer montoAReunir) { this.montoAReunir = montoAReunir; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setProponente(Proponente proponente) { this.proponente = proponente; }
    public void setRetornos(List<ETipoRetorno> retornos) { this.retornos = retornos; }
    public void setEstadoActual(Estado estadoActual) { this.estadoActual = estadoActual; }

    public void cambiarEstado(Estado nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.historialEstados.add(nuevoEstado);
    }
}


