public abstract class Violation {
    private String violationCode;
    private String dateOfViolation;
    private String subject;

    public Violation(String violationCode, String dateOfViolation, String subject) {
        this.violationCode = violationCode;
        this.dateOfViolation = dateOfViolation;
        this.subject = subject;
    }

    public String getViolationCode() {
        return violationCode;
    }

    public String getDateOfViolation() {
        return dateOfViolation;
    }

    public String getSubject() {
        return subject;
    }

    public abstract String getDescription();

    public String toCSV() {
        return violationCode + "," + dateOfViolation + "," + subject + "," + getDescription();
    }
}