package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.model.request.ProfesionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfesionResponse;

@Mapper
public class ProfesionMapperRest {

    public ProfesionResponse fromDomainToAdapterRestMaria(Profession profession) {
        return fromDomainToAdapterRest(profession, "MariaDB");
    }

    public ProfesionResponse fromDomainToAdapterRestMongo(Profession profession) {
        return fromDomainToAdapterRest(profession, "MongoDB");
    }

    public ProfesionResponse fromDomainToAdapterRest(Profession profession, String database) {
        return new ProfesionResponse(
                profession.getIdentification()+"",
                profession.getName(),
                profession.getDescription(),
                database,
                "OK");
    }

    public Profession fromAdapterToDomain(ProfesionRequest request)
    {
        Profession newProfession = new Profession();
        newProfession.setIdentification(Integer.parseInt(request.getIdentification()));
        newProfession.setName(request.getName());
        newProfession.setDescription(request.getDescription());
        return newProfession;
    }
}