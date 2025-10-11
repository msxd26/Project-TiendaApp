package pe.jsaire.tiendaapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.ArticuloService;

@RestController
@RequestMapping("/articulo")
@RequiredArgsConstructor
public class ArticuloController {

    private final ArticuloService articuloService;

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.findById(id));
    }
}
