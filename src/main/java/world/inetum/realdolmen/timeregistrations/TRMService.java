package world.inetum.realdolmen.timeregistrations;

import world.inetum.realdolmen.projects.Project;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TRMService {

    @PersistenceContext
    EntityManager entityManager;

    @RolesAllowed("MANAGER")
    public void storeProject(Project project) {
        entityManager.persist(project);
    }

    public List<Project> getAllProjects() {
        return entityManager.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

    public Project getProjectById(long id) {
        return entityManager
                .createQuery(
                        "select p from Project p left join fetch p.registrations where p.id = ?1",
                        Project.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    public List<TimeRegistration> getTimeRegistrationsForConsultant(String name) {
        return entityManager
                .createQuery(
                        "select tr from TimeRegistration tr where tr.consultant = ?1",
                        TimeRegistration.class)
                .setParameter(1, name)
                .getResultList();
    }

    public void register(
            long projectId,
            TimeRegistration timeRegistration
    ) {
        entityManager.find(Project.class, projectId, LockModeType.OPTIMISTIC_FORCE_INCREMENT)
                .addRegistration(timeRegistration);
        try {
            System.out.println("Sleeping to emulate heavy operation...");
            Thread.sleep(3000);
            System.out.println("...done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
