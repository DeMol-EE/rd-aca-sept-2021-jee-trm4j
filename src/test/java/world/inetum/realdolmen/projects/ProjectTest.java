package world.inetum.realdolmen.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import world.inetum.realdolmen.timeregistrations.TimeRegistration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

class ProjectTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void canNotAddMoreTimeThanAvailable() {
        Project sut = new Project();
        sut.setHours(100);
        TimeRegistration tr = new TimeRegistration();
        tr.setDate(LocalDate.now());
        tr.setHours(110);
        tr.setConsultant("John");
        sut.addRegistration(tr);
        Set<ConstraintViolation<Project>> violations = validator.validate(sut);
        boolean containsTotalHoursViolation = violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch("Sum of registered hours may not exceed available hours of project!"::equals);
        Assertions.assertTrue(containsTotalHoursViolation);
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, 0})
    void hoursMustBePositive(Integer hours) {
        Project sut = new Project();
        sut.setHours(hours);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Project>> violations = validator.validate(sut);
        boolean containsHoursPositiveViolation = violations.stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch("must be greater than 0"::equals);
        Assertions.assertTrue(containsHoursPositiveViolation);
    }

    @Test
    void timeRegistrationsLowerTimeLeft() {
        Project sut = new Project();
        sut.setHours(100);
        TimeRegistration tr = new TimeRegistration();
        tr.setHours(34);
        sut.addRegistration(tr);
        Assertions.assertEquals(66, sut.getTimeLeft());
    }
}