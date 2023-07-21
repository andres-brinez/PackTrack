package packTrack.Proyecto.modelos;

import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "Facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "envio_id")
    private Envio envio;
    private Date createdAt;

    private long montoCategoriaEnvio;
    private long montoPaquete;

    private long montoTotal;



    public Factura() {

    }

    public Factura(Envio envio) {

        this.envio = envio;
        this.id = generarCodigo();
        this.createdAt = Date.valueOf(java.time.LocalDate.now()); // Obtener la fecha actual

        // Al generar un factura se calcula el monto automaticamente
        calcularPorCategoriaEnvio(envio);
        calcularMontoPaquete(envio);

    }

    //Metodos

    private static int  generarCodigo() {

        int length = 5; // Longitud del código
        int min = (int) Math.pow(10, length - 1); // Valor mínimo (10000)
        int max = (int) Math.pow(10, length) - 1; // Valor máximo (99999)

        java.util.Random random = new java.util.Random();

        return random.nextInt(max - min + 1) + min;
    }

    private void calcularPorCategoriaEnvio(Envio envio){

        if(envio.getCategoria().equals("Express")){
            montoCategoriaEnvio= 20000;
        }else if(envio.getCategoria().equals("Estandar")){
            montoCategoriaEnvio= 15000;
        }
        montoCategoriaEnvio= 10000;
    }

    private void calcularMontoPaquete(Envio envio){

        // Tarifa dependiendo la clasificacion del paquete
        int TARIFA_BASICA = 10000;
        int TARIFA_EXTANDAR = 15000;
        int TARIFA_Dimensionado = 25000;

        double PORCENTAJE_VALOR_DECLARADO = 0.01; // 1% del valor declarado

        String clasificacion = envio.getPaquete().getClasificacion();
        float valorDeclarado = envio.getPaquete().getValorDeclarado();

        // Calcular el valor del paquete dependieno la clasificacion

        if(clasificacion.equals("Basico")){
            montoPaquete = TARIFA_BASICA + (int) (valorDeclarado * PORCENTAJE_VALOR_DECLARADO);
        }
        else if(clasificacion.equals("Estandar")){
            montoPaquete = TARIFA_EXTANDAR + (int) (valorDeclarado * PORCENTAJE_VALOR_DECLARADO);
        }
        else if(clasificacion.equals("Dimensionado")){
            montoPaquete = TARIFA_Dimensionado + (int) (valorDeclarado * PORCENTAJE_VALOR_DECLARADO);
        }
        else{
            montoPaquete = 0;
        }

    }

    public String generarFactura(){

        Envio envio = this.envio;

        String factura = "";

        // Calcular el monto del envio
        montoTotal= montoCategoriaEnvio + montoPaquete;

        // Generar la factura
        factura += "Factura de envio\n";
        factura += "ID: " + id + "\n";
        factura += "Fecha: " + createdAt + "\n";
        factura += "Nombre del remitente: " + envio.getPaquete().getUsuario().getNombre() + "\n";
        factura += "Nombre del empleado responsable: " + envio.getPaquete().getEmpleado().getNombre() + "\n";
        factura += "Origen: " + envio.getPaquete().getOrigen() + "\n";
        factura += "Destino: " + envio.getDestino() + "\n";
        factura += "Nombre del receptor: " + envio.getNombreReceptor() + "\n";
        factura += "Descripcion: " + envio.getDescripcion() + "\n";
        factura += "Categoria: " + envio.getCategoria() + "\n";
        factura += "Estado: " + envio.getEstado() + "\n";
        factura += "Fecha de envio: " + envio.getFechaEnvio() + "\n";
        factura += "Monto total: " + montoTotal + "\n";

        return factura;
    }


}


