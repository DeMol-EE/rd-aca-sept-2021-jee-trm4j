package world.inetum.realdolmen.projects;

import world.inetum.realdolmen.timeregistrations.TimeRegistration;
import world.inetum.realdolmen.validation.TotalHoursDoesNotExceedAvailable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROJECT")
@TotalHoursDoesNotExceedAvailable
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Positive
    @NotNull
    @Column(name = "hours")
    private Integer hours;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TimeRegistration> registrations = new HashSet<>();
    @Version
    @Column(name = "version")
    private Long version = 0L;

    public Project() {
    }

    public Project(int hours) {
        this.setHours(hours);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Set<TimeRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<TimeRegistration> registrations) {
        this.registrations.clear();
        registrations.forEach(this::addRegistration);
        this.registrations = registrations;
    }

    public void addRegistration(TimeRegistration timeRegistration) {
        timeRegistration.setProject(this);
        this.registrations.add(timeRegistration);
    }

    public int getTimeLeft() {
        return hours - getRegisteredTime();
    }

    private int getRegisteredTime() {
        return registrations.stream().mapToInt(TimeRegistration::getHours).sum();
    }
}
