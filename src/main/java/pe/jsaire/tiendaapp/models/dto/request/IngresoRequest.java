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
import pe.jsaire.tiendaapp.utils.validations.isExistsBySerieComprobanteIngreso;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngresoRequest implements Serializable {

    @NotNull(message = "El proveedor es requerido")
    @Positive(message = "El id del proveedor debe ser positivo")
    private Long idproveedor;

    @NotNull(message = "El usuario es requerido")
    @Positive(message = "El id del usuario debe ser positivo")
    private Long idusuario;

    @NotBlank(message = "El tipo de comprobante no puede estar en blanco")
    private String tipoComprobante;

    @NotBlank(message = "La serie del comprobante no puede estar en blanco")
    @isExistsBySerieComprobanteIngreso
    private String serieComprobante;

    @NotBlank(message = "El número de comprobante no puede estar en blanco")
    private String numComprobante;

    @NotBlank(message = "El estado no puede estar en blanco")
    private String estado;

    @NotEmpty(message = "Debe incluir al menos un detalle de ingreso")
    @Valid
    private List<DetalleIngresoRequest> detalles;
}
