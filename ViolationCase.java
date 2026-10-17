/**
 * Represents a single academic violation case record.
 */
public class ViolationCase {
    private String caseId;
    private Student student;
    private Violation violation;
    private Sanction sanction;
    private String status;
    private String dateReported;
    private String evidenceNotes;
    private String remarks;

    public ViolationCase(String caseId, Student student, Violation violation, 
                         Sanction sanction, String status, String dateReported, 
                         String evidenceNotes, String remarks) {
        this.caseId = caseId;
        this.student = student;
        this.violation = violation;
        this.sanction = sanction;
        this.status = status;
        this.dateReported = dateReported;
        this.evidenceNotes = evidenceNotes;
        this.remarks = remarks;
    }

    public String getCaseId() { return caseId; }
    public Student getStudent() { return student; }
    public Violation getViolation() { return violation; }
    
    public Sanction getSanction() { return sanction; }
    public void setSanction(Sanction sanction) { this.sanction = sanction; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEvidenceNotes() { return evidenceNotes; }
    public void setEvidenceNotes(String evidenceNotes) { this.evidenceNotes = evidenceNotes; }

    public void resolve(Sanction sanction, String remarks) {
        this.sanction = sanction;
        this.remarks = remarks;
        this.status = "RESOLVED";
    }

    public String toCSV() {
        String studentId = (student != null) ? student.getStudentId() : "UNKNOWN";
        String violCSV = (violation != null) ? violation.toCSV() : "NONE";
        String sancCSV = (sanction != null) ? sanction.toCSV() : "NONE";

        return caseId + ";" + studentId + ";" + violCSV + ";" + sancCSV + ";" + 
               status + ";" + dateReported + ";" + evidenceNotes + ";" + remarks;
    }

    @Override
    public String toString() {
        String studentName = (student != null) ? student.getName() : "Unknown";
        String violCode = (violation != null) ? violation.getViolationCode() : "N/A";
        String sancStr = (sanction != null) ? sanction.toString() : "None";

        return "Case ID: " + caseId + " | Student: " + studentName + 
               " | Violation Code: " + violCode + " | Status: " + status + 
               " | Sanction: " + sancStr;
    }
}