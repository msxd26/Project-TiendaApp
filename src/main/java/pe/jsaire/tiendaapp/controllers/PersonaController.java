package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.PersonaService;
import pe.jsaire.tiendaapp.models.dto.request.PersonaRequest;
import pe.jsaire.tiendaapp.models.dto.response.PersonaResponse;

@RestController
@RequestMapping("/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> getCategoria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personaService.findById(id));
    }


    @PostMapping
    public ResponseEntity<PersonaResponse> createCategoria(@Valid @RequestBody PersonaRequest personaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(personaRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponse> updateCategoria(@Valid @RequestBody PersonaRequest personaRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personaService.update(personaRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        personaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
