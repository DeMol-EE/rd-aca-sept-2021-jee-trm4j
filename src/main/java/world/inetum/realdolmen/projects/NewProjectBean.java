package world.inetum.realdolmen.projects;

import world.inetum.realdolmen.timeregistrations.TRMService;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class NewProjectBean {

    private final Project project = new Project(100);
    @EJB
    TRMService trmService;
    @Inject
    ProjectsBean projectsBean;

    public Project getProject() {
        return project;
    }

    public void createProject() {
        trmService.storeProject(project);
        projectsBean.getProjects().add(project);
    }

}
