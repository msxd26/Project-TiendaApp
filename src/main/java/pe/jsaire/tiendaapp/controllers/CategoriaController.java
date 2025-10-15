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
import pe.jsaire.tiendaapp.infraestructures.abstract_services.CategoriaService;
import pe.jsaire.tiendaapp.models.dto.request.CategoriaRequest;
import pe.jsaire.tiendaapp.models.dto.response.CategoriaResponse;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;


    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> getCategoria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findById(id));
    }


    @PostMapping
    public ResponseEntity<CategoriaResponse> createCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoriaRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> updateCategoria(@Valid @RequestBody CategoriaRequest categoriaRequest, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.update(categoriaRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
