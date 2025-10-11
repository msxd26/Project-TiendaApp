package pe.jsaire.tiendaapp.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.jsaire.tiendaapp.utils.enums.TipoPersona;

@Entity(name = "persona")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    @Enumerated(EnumType.STRING)
    private TipoPersona tipoPersona;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String email;
    
}
