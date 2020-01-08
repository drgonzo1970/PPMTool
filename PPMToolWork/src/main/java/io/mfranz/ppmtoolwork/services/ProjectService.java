package io.mfranz.ppmtoolwork.services;

import com.sun.org.apache.xpath.internal.objects.XString;
import io.mfranz.ppmtoolwork.domain.Project;
import io.mfranz.ppmtoolwork.exceptions.ProjectIdException;
import io.mfranz.ppmtoolwork.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

///////////////////////////////////////////
//Logik erfolgt hier in der Service-Layer//
///////////////////////////////////////////
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch(Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findByProjectIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+projectId+"' does not exists");
        }
        return project;
    }

    public Project findByProjectId(Long Id){
        Optional<Project> optionalProject = projectRepository.findById(Id);

        if(!optionalProject.isPresent() ){
            throw new ProjectIdException("Project ID '"+Id+"' does not exists");
        }
        return optionalProject.get();
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Can not delete Project with ID '"+projectId+"'. This project does not exists.");
        }
        projectRepository.delete(project);
    }

    public List<Project> saveOrUpdateProjects(List<Project> projects) {
        final List<Project> createdProjects = new ArrayList<>();
        for (Project project : projects){
            createdProjects.add(saveOrUpdateProject(project));
        }

        return createdProjects;
    }
}
