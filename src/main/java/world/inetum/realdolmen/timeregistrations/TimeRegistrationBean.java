package world.inetum.realdolmen.timeregistrations;

import world.inetum.realdolmen.projects.Project;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: separate state + ajaxify
@Model
public class TimeRegistrationBean implements Serializable {

    private final TimeRegistration timeRegistration = new TimeRegistration();
    @EJB
    TRMService trmService;
    @Inject
    Principal principal;
    @NotNull
    private Long projectId;
    private List<Project> projects;

    private YearMonth yearMonth = YearMonth.now();
    private List<LocalDate> daysOfMonth = Collections.emptyList();
    private Map<LocalDate, List<TimeRegistration>> timeRegistrations;

    @PostConstruct
    public void onLoad() {
        this.projects = trmService.getAllProjects();
        this.timeRegistrations = trmService
                .getTimeRegistrationsForConsultant(principal.getName())
                .stream()
                .collect(Collectors.groupingBy(TimeRegistration::getDate));
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();
        this.daysOfMonth = Stream.iterate(
                start,
                d -> d.isBefore(end),
                d -> d.plusDays(1)
        ).collect(Collectors.toList());
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<TimeRegistration> getTimeRegistrationsForDate(LocalDate date) {
        return this.timeRegistrations.getOrDefault(date, Collections.emptyList());
    }

    public TimeRegistration getTimeRegistration() {
        return timeRegistration;
    }

    public List<LocalDate> getDaysOfMonth() {
        return daysOfMonth;
    }

    public String register() {
        timeRegistration.setConsultant(principal.getName());
        try {
            this.trmService.register(projectId, timeRegistration);
        } catch (Exception e) {
            var msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "TR failed", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        return "/time-registrations?faces-redirect=true";
    }
}
