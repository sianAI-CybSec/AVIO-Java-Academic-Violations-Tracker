/**
 * Class representing academic cheating offenses.
 */
public class AcademicCheating extends Violation {
    private String assessmentType;

    public AcademicCheating(String violationCode, String dateOfViolation, String subject, String assessmentType) {
        super(violationCode, dateOfViolation, subject);
        this.assessmentType = assessmentType;
    }

    public String getAssessmentType() { return assessmentType; }

    @Override
    public String getDescription() {
        // Simple conditional to return description based on violation code
        String code = getViolationCode().toUpperCase();
        if (code.equals("AC-PAM")) {
            return "Unauthorized possession of academic materials relative to examinations";
        } else if (code.equals("AC-C1")) {
            return "Copying from another's exam paper";
        } else if (code.equals("AC-C2")) {
            return "Allowing to copy another from own exam paper";
        } else if (code.equals("AC-P")) {
            return "Plagiarism";
        } else if (code.equals("AC-ED")) {
            return "Unauthorized use of electronic devices during exam";
        } else if (code.equals("AC-OC")) {
            return "Cheating during online classes";
        } else if (code.equals("AC-AI")) {
            return "Unauthorized AI use to complete academic requirements";
        }
        return "Academic Cheating Violation";
    }

    @Override
    public String toCSV() {
        return "CHEATING," + getViolationCode() + "," + getDateOfViolation() + "," + getSubject() + "," + assessmentType;
    }
}