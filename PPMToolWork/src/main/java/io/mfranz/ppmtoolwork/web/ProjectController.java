package io.mfranz.ppmtoolwork.web;

import io.mfranz.ppmtoolwork.domain.Project;
import io.mfranz.ppmtoolwork.services.MapValidationErrorService;
import io.mfranz.ppmtoolwork.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;
//was macht dieses Autowired genau?
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    //was macht dieses PostMapping genau?
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }
    @PostMapping("/batch")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody List<Project> projects, BindingResult result){

        final ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        List<Project> createdProjects = projectService.saveOrUpdateProjects(projects);
        return new ResponseEntity<>(createdProjects, HttpStatus.CREATED);
    }

    //string im Path und in PathVariable müssen identisch sein (projectId - projectId)
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){
        Project project = projectService.findByProjectIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long Id){
        Project project = projectService.findByProjectId(Id);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }


    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){return projectService.findAllProjects();}
//wieso wird projectService mit kleinem p geschrieben? Im Package ProjectService ist die klasse doch mit großem P geschrieben
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);
        return new ResponseEntity<String> ("Project with ID: '"+projectId+"' was deleted.", HttpStatus.OK);
    }

    // Aufgabe: Alle projecte ausgeben die den übergebenen projectName haben
}
