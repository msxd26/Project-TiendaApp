package pe.jsaire.tiendaapp.infraestructures.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.PersonaService;
import pe.jsaire.tiendaapp.mappers.PersonaMapper;
import pe.jsaire.tiendaapp.models.dto.request.PersonaRequest;
import pe.jsaire.tiendaapp.models.dto.response.PersonaResponse;
import pe.jsaire.tiendaapp.models.entities.Persona;
import pe.jsaire.tiendaapp.models.repositories.PersonaRepository;
import pe.jsaire.tiendaapp.utils.exceptions.PersonaNotFoundException;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @Override
    @Transactional(readOnly = true)
    public PersonaResponse findById(Long id) {
        return personaMapper.toResponse(personaRepository.findById(id).orElseThrow(() ->
                new PersonaNotFoundException("No se encontro el persona con el id: " + id)));
    }

    @Override
    @Transactional
    public PersonaResponse save(PersonaRequest personaRequest) {
        return personaMapper.toResponse(personaRepository.save(personaMapper.toEntity(personaRequest)));
    }

    @Override
    @Transactional
    public PersonaResponse update(PersonaRequest personaRequest, Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException("No se encontro el persona con el id: " + id));

        persona.setNombre(personaRequest.getNombre());
        persona.setTipoDocumento(personaRequest.getTipoDocumento());
        persona.setNumeroDocumento(personaRequest.getNumDocumento());
        persona.setDireccion(personaRequest.getDireccion());
        persona.setEmail(personaRequest.getEmail());
        persona.setTelefono(personaRequest.getTelefono());
        return personaMapper.toResponse(personaRepository.save(personaMapper.toEntity(personaRequest)));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (!personaRepository.existsById(id)) {
            throw new PersonaNotFoundException("No existe el persona con el id: " + id);
        }
        personaRepository.deleteById(id);
    }
}
