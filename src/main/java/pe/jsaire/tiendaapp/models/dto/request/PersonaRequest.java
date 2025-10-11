package pe.jsaire.tiendaapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {
    private String tipoPersona;
    private String nombre;
    private String tipoDocumento;
    private String numDocumento;
    private String direccion;
    private String telefono;
    private String email;
}
