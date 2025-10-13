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

@Entity(name = "ingreso")
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "idingreso")
    private Long idIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproveedor")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_comprobante")
    private TipoComprobante tipoComprobante;

    @Column(name = "serie_comprobante")
    private String serieComprobante;

    @Column(name = "num_comprobante")
    private String numeroComprobante;
    private LocalDate fecha;
    private BigDecimal impuesto;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoTransaccion estado;


    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ingreso")
    private Set<DetalleIngreso> detalleIngresos;

    @Transient
    private BigDecimal IGV = new BigDecimal("0.18");

    public Ingreso() {
        this.detalleIngresos = new HashSet<>();
        this.fecha = LocalDate.now();
        this.total = BigDecimal.ZERO;
        this.impuesto = BigDecimal.ZERO;
    }

    public void calcularTotales() {
        if (detalleIngresos != null && !detalleIngresos.isEmpty()) {
            BigDecimal subtotal = detalleIngresos.stream()
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

    public void addDetalleIngreso(DetalleIngreso detalleIngreso) {
        if (detalleIngreso == null) {
            this.detalleIngresos = new HashSet<>();
        }
        this.detalleIngresos.add(detalleIngreso);
        detalleIngreso.setIngreso(this);
        calcularTotales();
    }

    public void removeDetalleIngreso(DetalleIngreso detalleIngreso) {
        if (this.detalleIngresos != null) {
            this.detalleIngresos.remove(detalleIngreso);
            detalleIngreso.setIngreso(null);
            calcularTotales();
        }
    }


}
