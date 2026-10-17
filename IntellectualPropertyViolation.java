/**
 * Class representing intellectual property violations.
 */
public class IntellectualPropertyViolation extends Violation {
    private String workDescription;

    public IntellectualPropertyViolation(String violationCode, String dateOfViolation, String subject, String workDescription) {
        super(violationCode, dateOfViolation, subject);
        this.workDescription = workDescription;
    }

    public String getWorkDescription() { return workDescription; }

    @Override
    public String getDescription() {
        // Simple conditional for description
        if (getViolationCode().equalsIgnoreCase("IPV")) {
            return "Violation of HAU Intellectual Property Policies";
        }
        return "Intellectual Property Violation";
    }

    @Override
    public String toCSV() {
        return "IP_VIOLATION," + getViolationCode() + "," + getDateOfViolation() + "," + getSubject() + "," + workDescription;
    }
}