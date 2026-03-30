package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.tiendaapp.utils.validations.isExistByNombreArticulo;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloRequest implements Serializable {

    @NotNull(message = "La categoría es requerida")
    @Positive(message = "El id de categoría debe ser positivo")
    private Long idcategoria;

    @NotBlank(message = "El código no puede estar en blanco")
    private String codigo;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @isExistByNombreArticulo
    private String nombre;

    @NotNull(message = "El precio de venta es requerido")
    @DecimalMin(value = "0.1", message = "El precio de venta debe ser al menos 0.1")
    @Positive(message = "El precio de venta debe ser positivo")
    private BigDecimal precioVenta;

    @NotNull(message = "El stock es requerido")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "La descripción no puede estar en blanco")
    private String descripcion;
}
