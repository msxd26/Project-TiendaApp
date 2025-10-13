package pe.jsaire.tiendaapp.infraestructures.abstract_services;

public interface GenericService<RQ, RS, ID> {

    RS findById(ID id);

    RS save(RQ rq);

    RS update(RQ rq, ID id);

    void delete(ID id);
}
