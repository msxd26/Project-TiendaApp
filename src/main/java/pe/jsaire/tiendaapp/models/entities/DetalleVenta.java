package pe.jsaire.tiendaapp.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "detalle_venta")
@Setter
@Getter
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetalle_venta")
    private Long idDetalleVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idventa")
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idarticulo")
    private Articulo articulo;

    private Integer cantidad;

    private BigDecimal precio;
    private BigDecimal descuento;

    public DetalleVenta() {
        this.precio = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
    }
}
