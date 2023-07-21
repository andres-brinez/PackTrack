package packTrack.Proyecto.modelos;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Random;

@Entity
@Table(name = "Envios")
public class Envio {

    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "paquete_id")
    private Paquete Paquete;

    private String destino;
    private String nombreReceptor;
    private String descripcion;
    private String categoria;
    private String estado;
    private String notaSeguimiento;
    private String ubicacionActual;
    private Date fechaEnvio;
    private Date fechaRecepcion;



    public Envio() {
    }

    public Envio(packTrack.Proyecto.modelos.Paquete paquete, String destino, String nombreReceptor, String descripcion, String categoria) {

        Paquete = paquete;
        this.destino = destino;
        this.nombreReceptor = nombreReceptor;
        this.descripcion = descripcion;
        this.categoria = categoria;

        this.id= generarCodigo();
        this.estado = "Enviando";
        this.fechaEnvio = generarFecha();


    }

    // METODOS
    private static int  generarCodigo() {

        int length = 5; // Longitud del código
        int min = (int) Math.pow(10, length - 1); // Valor mínimo (10000)
        int max = (int) Math.pow(10, length) - 1; // Valor máximo (99999)

        Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }

    private static Date generarFecha() {

        return Date.valueOf(java.time.LocalDate.now());

    }

    // GETTERS Y SETTERS


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public packTrack.Proyecto.modelos.Paquete getPaquete() {
        return Paquete;
    }

    public void setPaquete(packTrack.Proyecto.modelos.Paquete paquete) {
        Paquete = paquete;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotaSeguimiento() {
        return notaSeguimiento;
    }

    public void setNotaSeguimiento(String notaSeguimiento) {
        this.notaSeguimiento = notaSeguimiento;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "id=" + id +
                ", Paquete=" + Paquete.getNombre() +
                ", Remitente=" + Paquete.getUsuario().getNombre()+
                ", Empleado Encargado=" + Paquete.getEmpleado().getNombre() +
                ", destino='" + destino + '\'' +
                ", nombreReceptor='" + nombreReceptor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                ", notaSeguimiento='" + notaSeguimiento + '\'' +
                ", ubicacionActual='" + ubicacionActual + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", fechaRecepcion=" + fechaRecepcion +
                '}';
    }
}
