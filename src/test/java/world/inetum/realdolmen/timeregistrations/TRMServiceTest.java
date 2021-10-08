package world.inetum.realdolmen.timeregistrations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import world.inetum.realdolmen.DatabaseTestUtils;
import world.inetum.realdolmen.projects.Project;

import java.time.LocalDate;

class TRMServiceTest {

    @BeforeEach
    void cleanDb() {
        DatabaseTestUtils.doInTx(em -> em
                .createQuery("delete from Project p")
                .executeUpdate());
    }

    @Test
    void addRegistration() {
        // arrange
        Project p = new Project(100);
        DatabaseTestUtils.doInTx(em -> em.persist(p));
        long projectId = p.getId();
        TimeRegistration tr = new TimeRegistration();
        tr.setDate(LocalDate.now());
        tr.setHours(8);
        tr.setConsultant("John");
        TRMService sut = new TRMService();
        // act
        DatabaseTestUtils.doInTx(em -> {
            sut.entityManager = em;
            sut.register(projectId, tr);
        });
        // assert
        Project pr = DatabaseTestUtils.getEntityManager().find(Project.class, projectId);
//        Assertions.assertEquals(2, pr.getVersion());
        Assertions.assertEquals(92, pr.getTimeLeft());
        pr.getRegistrations().forEach(r -> Assertions.assertNotNull(r.getId()));
    }

    @Test
    @Disabled("BV is not triggered when doing JPA testing")
    void canNotRegisterMoreTimeThanAvailable() {
        // arrange
        Project p = new Project(100);
        DatabaseTestUtils.doInTx(em -> em.persist(p));
        long projectId = p.getId();
        TimeRegistration tr = new TimeRegistration();
        tr.setDate(LocalDate.now());
        tr.setHours(101);
        tr.setConsultant("John");
        TRMService sut = new TRMService();
        // act
        DatabaseTestUtils.doInTx(em -> {
            sut.entityManager = em;
            sut.register(projectId, tr);
        });
        // assert
    }
}