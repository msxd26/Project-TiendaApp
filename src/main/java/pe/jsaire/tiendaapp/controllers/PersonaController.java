package pe.jsaire.tiendaapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.PersonaService;
import pe.jsaire.tiendaapp.models.dto.request.PersonaRequest;
import pe.jsaire.tiendaapp.models.dto.response.PersonaResponse;
import pe.jsaire.tiendaapp.security.AuthorityConstants;
import pe.jsaire.tiendaapp.utils.enums.SortType;

@RestController
@RequestMapping("/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping
    @PreAuthorize(AuthorityConstants.READ_PEOPLE)
    public ResponseEntity<Page<PersonaResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "NONE") SortType sortType) {
        return ResponseEntity.ok(personaService.getAll(page, size, sortType));
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthorityConstants.READ_PEOPLE)
    public ResponseEntity<PersonaResponse> getPersona(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personaService.findById(id));
    }

    @PostMapping
    @PreAuthorize(AuthorityConstants.WRITE_PERSON)
    public ResponseEntity<PersonaResponse> createPersona(@Valid @RequestBody PersonaRequest personaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(personaRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<PersonaResponse> updatePersona(@Valid @RequestBody PersonaRequest personaRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personaService.update(personaRequest, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AuthorityConstants.ADMIN_ONLY)
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        personaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
