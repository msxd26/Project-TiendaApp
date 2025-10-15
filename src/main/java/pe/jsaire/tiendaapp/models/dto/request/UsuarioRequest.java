package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.tiendaapp.utils.validations.isExistsByEmail;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest implements Serializable {

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String nombre;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String tipoDocumento;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String numDocumento;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String direccion;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String telefono;

    @NotBlank(message = "El campo  no puede estar en blanco")
    @isExistsByEmail
    private String email;

    @NotBlank(message = "El campo  no puede estar en blanco")
    private String password;

    private boolean admin;
}
