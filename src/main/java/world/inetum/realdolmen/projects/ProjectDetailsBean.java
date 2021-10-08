package world.inetum.realdolmen.projects;

import javax.enterprise.inject.Model;

@Model
public class ProjectDetailsBean {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
