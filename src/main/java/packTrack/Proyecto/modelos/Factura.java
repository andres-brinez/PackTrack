package packTrack.Proyecto.modelos;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "Facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long idEnvio;
    private Date createdAt;
    private long monto;


    public Factura() {

    }

    public Factura(long idEnvio) {
        this.idEnvio = idEnvio;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }
}
