public class IntellectualPropertyViolation extends Violation {
    private String workDescription;

    public IntellectualPropertyViolation(String violationCode, String dateOfViolation,
                                         String subject, String workDescription) {
        super(violationCode, dateOfViolation, subject);
        this.workDescription = workDescription;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    @Override
    public String getDescription() {
        return "Intellectual property violation involving " + workDescription;
    }
}