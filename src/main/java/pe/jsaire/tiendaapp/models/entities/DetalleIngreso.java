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
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "detalle_ingreso")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleIngreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetalle_ingreso")
    private Long idDetalleIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idingreso")
    private Ingreso ingreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idarticulo")
    private Articulo articulo;

    private Integer cantidad;
    private BigDecimal precio;
}
