package co.edu.javeriana.as.personapp.mariadb.adapter;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.EstudiosMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.EstudioRepositoryMaria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Adapter("studyOutputAdapterMaria") 
@Transactional 
public class StudyOutputAdapterMaria implements StudyOutputPort {

    @Autowired
    private EstudioRepositoryMaria estudioRepositoryMaria;

    @Autowired
    private EstudiosMapperMaria estudioMapperMaria; 

    @Override
    public Study save(Study study) {
        log.debug("Into save on Adapter MariaDB");
        EstudiosEntity persistedEstudio = estudioRepositoryMaria.save(estudioMapperMaria.fromDomainToAdapter(study));
        return estudioMapperMaria.fromAdapterToDomain(persistedEstudio);
    }

    @Override
    public Boolean delete(Integer professionID, Integer personID) {
        log.debug("Into delete on Adapter MariaDB");
        EstudiosEntity estudios = estudioRepositoryMaria.findByProfesionAndPersona(professionID, personID);
        if (estudios != null) {
            estudioRepositoryMaria.delete(estudios);
            return estudioRepositoryMaria.findByProfesionAndPersona(professionID, personID) == null;
        }
        return false; 
    }

    @Override
    public List<Study> find() {
        log.debug("Into find on Adapter MariaDB");
        return estudioRepositoryMaria.findAll().stream()
                .map(estudioMapperMaria::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Study findById(Integer professionID, Integer personID) {
        log.debug("Into findById on Adapter MariaDB");
        EstudiosEntity estudios = estudioRepositoryMaria.findByProfesionAndPersona(professionID, personID);
        return estudios != null ? estudioMapperMaria.fromAdapterToDomain(estudios) : null; 
    }
}