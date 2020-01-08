package io.mfranz.ppmtoolwork.repositories;

import io.mfranz.ppmtoolwork.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);

    Project findByProjectIdentifier (String projectID);

    @Override
    Iterable<Project> findAll();


}
