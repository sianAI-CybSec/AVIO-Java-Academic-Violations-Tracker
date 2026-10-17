public class AcademicCheating extends Violation {
    private String assessmentType;

    public AcademicCheating(String violationCode, String dateOfViolation,
                            String subject, String assessmentType) {
        super(violationCode, dateOfViolation, subject);
        this.assessmentType = assessmentType;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    @Override
    public String getDescription() {
        return "Academic cheating during " + assessmentType;
    }
}