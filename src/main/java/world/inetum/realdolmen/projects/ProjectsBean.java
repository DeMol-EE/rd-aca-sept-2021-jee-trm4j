package world.inetum.realdolmen.projects;

import world.inetum.realdolmen.timeregistrations.TRMService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProjectsBean implements Serializable {

    @EJB // EJBs do not need to be serializable, the generated proxies are!
    TRMService trmService;
    private List<Project> projects;

    @PostConstruct
    private void loadProjects() {
        projects = trmService.getAllProjects();
    }

    public List<Project> getProjects() {
        return projects;
    }

}
