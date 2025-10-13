package pe.jsaire.tiendaapp.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.jsaire.tiendaapp.utils.enums.EstadoTransaccion;
import pe.jsaire.tiendaapp.utils.enums.TipoComprobante;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "venta")
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "idventa")
    private Long idVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprobante", columnDefinition = "tipo_comprobante_enum")
    private TipoComprobante tipoComprobante;

    @Column(name = "serie_comprobante")
    private String serieComprobante;

    @Column(name = "num_comprobante")
    private String numeroComprobante;

    @Column(name = "fecha_hora")
    private LocalDate fechaHora;
    private BigDecimal impuesto;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "estado_transaccion_enum")
    private EstadoTransaccion estado;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "venta", fetch = FetchType.EAGER)
    private Set<DetalleVenta> detalleVentas;

    @Transient
    private BigDecimal IGV = new BigDecimal("0.18");

    public Venta() {
        this.detalleVentas = new HashSet<>();
        this.fechaHora = LocalDate.now();
        this.impuesto = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }


    public void calcularTotales() {
        if (detalleVentas != null && !detalleVentas.isEmpty()) {
            BigDecimal subtotal = detalleVentas.stream()
                    .map(detalle -> detalle.getPrecio()
                            .multiply(BigDecimal.valueOf(detalle.getCantidad())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            this.impuesto = subtotal.multiply(IGV);
            this.total = subtotal.add(impuesto);
        } else {
            this.impuesto = BigDecimal.ZERO;
            this.total = BigDecimal.ZERO;
        }
    }

    public void addDetalleVenta(DetalleVenta detalleVenta) {
        if (this.detalleVentas == null) {
            this.detalleVentas = new HashSet<>();
        }
        this.detalleVentas.add(detalleVenta);
        detalleVenta.setVenta(this);
        calcularTotales();
    }

    public void removeDetalleVenta(DetalleVenta detalleVenta) {
        if (this.detalleVentas != null) {
            this.detalleVentas.remove(detalleVenta);
            detalleVenta.setVenta(null);
            calcularTotales();
        }
    }


}
