package pe.jsaire.tiendaapp.models.entities;

import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
    private Long idVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipoComprobante;
    private String serieComprobante;
    private String numeroComprobante;
    private LocalDate fechaHora;
    private BigDecimal impuesto;
    private BigDecimal total;
    private Boolean estado;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "venta")
    private Set<DetalleVenta> detalleVentas;


    public Venta() {
        this.detalleVentas = new HashSet<>();
    }

    public void addDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVentas.add(detalleVenta);
        detalleVenta.setVenta(this);
    }

    public void removeDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVentas.remove(detalleVenta);
        detalleVenta.setVenta(null);
    }

    public BigDecimal calculateTotal() {
        this.total = detalleVentas.stream().
                map(detalleVenta -> detalleVenta.getPrecio().add(BigDecimal.valueOf(detalleVenta.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }
}
