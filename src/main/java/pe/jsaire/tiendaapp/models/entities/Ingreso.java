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

@Entity(name = "ingreso")
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproveedor")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private TipoComprobante tipoComprobante;

    private String serieComprobante;
    private String numeroComprobante;
    private LocalDate fecha;
    private BigDecimal impuesto;
    private BigDecimal total;
    private Boolean estado;


    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ingreso")
    private Set<DetalleIngreso> detalleIngresos;

    public Ingreso() {
        this.detalleIngresos = new HashSet<>();
    }

    public void addDetalleIngreso(DetalleIngreso detalleIngreso) {
        this.detalleIngresos.add(detalleIngreso);
        detalleIngreso.setIngreso(this);
    }

    public void removeDetalleIngreso(DetalleIngreso detalleIngreso) {
        this.detalleIngresos.remove(detalleIngreso);
        detalleIngreso.setIngreso(null);
    }

    public BigDecimal calculateTotal() {
        this.total = detalleIngresos.stream().
                map(detalleIngreso -> detalleIngreso.getPrecio().add(BigDecimal.valueOf(detalleIngreso.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }
}
