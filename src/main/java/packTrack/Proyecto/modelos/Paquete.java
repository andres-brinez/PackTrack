package packTrack.Proyecto.modelos;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Paquetes")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private  Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Usuario empleado;

    private String nombre;
    private String origen;
    private String descripcion;
    private String estado;
    private String clasificacion;

    private int cantidad;
    private int peso;
    private int altura;
    private int ancho;
    private int largo;
    private float valorDeclarado;
    private Date createdAt;

    public Paquete() {
    }

public Paquete(Usuario usuario, Usuario empleado, String nombre, String origen, String descripcion, int cantidad, int peso, int altura, int ancho, int largo, float valorDeclarado) {

        this.usuario = usuario;
        this.empleado = empleado;
        this.nombre = nombre;
        this.origen = origen;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.peso = peso;
        this.altura = altura;
        this.ancho = ancho;
        this.largo = largo;
        this.valorDeclarado = valorDeclarado;

        this.createdAt = new Date(); // Obtener la fecha actual
        this.estado = "Registrado";
        this.clasificacion = generarClasificacion();

    }

    // Método para generar la clasificación del paquete
    public String generarClasificacion() {
        if (peso <= 2 && altura <= 10 && ancho <= 20 && largo <= 30) {
            return "Básico";
        } else if (peso <= 10 && altura <= 30 && ancho <= 40 && largo <= 50) {
            return "Estándar";
        } else {
            return "Dimensionado";
        }
    }

    // Getters y Setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public float getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(float valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", empleado=" + empleado +
                ", nombre='" + nombre + '\'' +
                ", origen='" + origen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", clasificacion='" + clasificacion + '\'' +
                ", cantidad=" + cantidad +
                ", peso=" + peso +
                ", altura=" + altura +
                ", ancho=" + ancho +
                ", largo=" + largo +
                ", valorDeclarado=" + valorDeclarado +
                ", createdAt=" + createdAt +
                '}';
    }
}


