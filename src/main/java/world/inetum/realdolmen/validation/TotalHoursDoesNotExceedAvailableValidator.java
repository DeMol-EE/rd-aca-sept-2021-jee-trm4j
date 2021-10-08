package world.inetum.realdolmen.validation;

import world.inetum.realdolmen.projects.Project;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TotalHoursDoesNotExceedAvailableValidator implements ConstraintValidator<TotalHoursDoesNotExceedAvailable, Project> {
    @Override
    public boolean isValid(Project value, ConstraintValidatorContext context) {
        return value.getTimeLeft() >= 0;
    }
}
