package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.tiendaapp.utils.validations.isExistsByEmail;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest implements Serializable {

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo de documento no puede estar en blanco")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento no puede estar en blanco")
    @Size(min = 8, max = 20, message = "El número de documento debe tener entre 8 y 20 caracteres")
    private String numDocumento;

    @NotBlank(message = "La dirección no puede estar en blanco")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 caracteres")
    private String telefono;

    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "Ingrese un correo electrónico válido")
    @isExistsByEmail
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 4, max = 100, message = "La contraseña debe tener entre 4 y 100 caracteres")
    private String password;

    private boolean admin;

    private Set<RolRequest> roles;
}
