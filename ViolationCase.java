import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
  Represents an individual case tracking an academic integrity incident.
  Combines a Student, a specific Violation, an assigned Sanction, and case data.
 */
public class ViolationCase {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private String caseId;
    private Student student;
    private Violation violation;
    private LocalDate incidentDate;
    private Sanction sanction;
    private boolean resolved;

    /* Default Constructor.*/
    public ViolationCase() {
        this.caseId = "";
        this.student = null;
        this.violation = null;
        this.incidentDate = LocalDate.now();
        this.sanction = Sanction.WRITTEN_WARNING;
        this.resolved = false;
    }

    /**
     Parameterized Constructor.
     @param caseId       Unique case code.
     @param student      Student associated with case.
     @param violation    Violation object.
     @param incidentDate Date of incident.
     @param sanction     Assigned Sanction.
     @param resolved     Resolution status.
     */
    public ViolationCase(String caseId, Student student, Violation violation,
                         LocalDate incidentDate, Sanction sanction, boolean resolved) {
        this.caseId = caseId;
        this.student = student;
        this.violation = violation;
        this.incidentDate = incidentDate;
        this.sanction = sanction;
        this.resolved = resolved;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Sanction getSanction() {
        return sanction;
    }

    public void setSanction(Sanction sanction) {
        this.sanction = sanction;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String toCSV() {
        String studentId = (student != null) ? student.getStudentId() : "UNKNOWN";
        String violationCSV = (violation != null) ? violation.toCSV() : "NONE";
        String dateStr = (incidentDate != null) ? incidentDate.format(DATE_FORMATTER) : LocalDate.now().format(DATE_FORMATTER);
        String sanctionStr = (sanction != null) ? sanction.name() : Sanction.WRITTEN_WARNING.name();

        return String.format("%s;%s;%s;%s;%s;%b",
                caseId, studentId, violationCSV, dateStr, sanctionStr, resolved);
    }

    @Override
    public String toString() {
        String studentName = (student != null) ? student.getName() : "Unknown Student";
        String violTitle = (violation != null) ? violation.getTitle() : "No Violation Assigned";
        int severity = (violation != null) ? violation.calculateSeverityLevel() : 0;
        String sanctionText = (sanction != null) ? sanction.getLabel() : "None";

        return String.format("Case ID: %s | Student: %s | Violation: %s | Severity: %d | Sanction: %s | Date: %s | Resolved: %s",
                caseId, studentName, violTitle, severity, sanctionText, incidentDate, resolved ? "Yes" : "No");
    }
}