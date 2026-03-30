package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaRequest implements Serializable {

    @NotNull(message = "El cliente es requerido")
    @Positive(message = "El id del cliente debe ser positivo")
    private Long idcliente;

    @NotNull(message = "El usuario es requerido")
    @Positive(message = "El id del usuario debe ser positivo")
    private Long idusuario;

    @NotBlank(message = "El tipo de comprobante no puede estar en blanco")
    private String tipoComprobante;

    @NotBlank(message = "La serie del comprobante no puede estar en blanco")
    private String serieComprobante;

    @NotBlank(message = "El número de comprobante no puede estar en blanco")
    private String numComprobante;

    @NotBlank(message = "El estado no puede estar en blanco")
    private String estado;

    @NotEmpty(message = "Debe incluir al menos un detalle de venta")
    @Valid
    private List<DetalleVentaRequest> detalles;
}
