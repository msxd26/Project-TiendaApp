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

@Entity(name = "articulo")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idarticulo")
    private Long idArticulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcategoria")
    private Categoria categoria;

    private String codigo;
    private String nombre;
    private BigDecimal precioVenta;
    private int stock;
    private String descripcion;
    private Boolean estado;

}
