package pe.jsaire.tiendaapp.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest implements Serializable {

    @NotBlank(message = "Este campo no puede estar en blanco")
    private String nombre;

    @NotBlank(message = "Este campo no puede estar en blanco")
    private String descripcion;

}
