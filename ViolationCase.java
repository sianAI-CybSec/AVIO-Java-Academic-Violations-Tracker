public class ViolationCase {
    private String caseId;
    private Student student;
    private Violation violation;
    private Sanction sanction;
    private String status;
    private String dateReported;
    private String evidenceNotes;
    private String remarks;

    public ViolationCase(
        String caseId, Student student, Violation violation,
        Sanction sanction, String status, String dateReported,
        String evidenceNotes, String remarks
    ){
        this.caseId = caseId;
        this.student = student;
        this.violation = violation;
        this.sanction = sanction;
        this.status = status;
        this.dateReported = dateReported;
        this.evidenceNotes = evidenceNotes;
        this.remarks = remarks;
    }

    // getter and setter methods
    public String getCaseId(){return caseId;}
    public Student getStudent(){return student;}
    public Violation getViolation(){return violation;}
    public Sanction getSanction(){return sanction;}
    public void setSanction(Sanction s){sanction = s;}
    public String getStatus(){return status;}
    public void setStatus(String stat){status = stat;}
    public String getEvidenceNotes(){return evidenceNotes;}
    public void setEvidenceNotes(String en){evidenceNotes = en;}

    public void resolve(Sanction sanctionObj, String remString){
        if (sanctionObj == null){
            throw new IllegalArgumentException("A sanction must be provided for the violation case.");
        }
        if (status.equalsIgnoreCase("RESOLVED")){
            throw new IllegalStateException(String.format("Case %s is already resolved.",caseId));
        }
        if (status.equalsIgnoreCase("ARCHIVED")){
            throw new IllegalStateException(String.format("Case %s is archived and cannot be edited.",caseId));
        }
        this.sanction = sanctionObj;
        this.remarks = (remarks == null) ? "" : remarks.trim();
        this.status = "RESOLVED";
    }

    public String toCSV(){
        String sanctionPart;
        if (sanction == null){
            sanctionPart = ", ,";
        } else {
            sanctionPart = sanction.toCSV();
        }

        FileManager fm = new FileManager();

        String csvResult = fm.escapeCsv(caseId) + ","
         + fm.escapeCsv(student.getStudentId()) + ","
         + violation.toCSV() + ","         
         + fm.escapeCsv(status) + ","
         + dateReported + ","
         + fm.escapeCsv(evidenceNotes) + ","
         + sanctionPart + ","
         + fm.escapeCsv(remarks);

         return csvResult;
    }

    @Override
    public String toString() {
        StringBuilder vioCaseStr = new StringBuilder();

        vioCaseStr.append("Case ID        : ").append(caseId).append("\n");
        vioCaseStr.append("Student        : ").append(student.getName()).append(" (").append(student.getStudentId()).append(")\n");
        vioCaseStr.append("Violation      : ").append(violation.getViolationCode()).append(" - ").append(violation.getDescription()).append("\n");
        vioCaseStr.append("Date of Violation: ").append(violation.getDateOfViolation()).append("\n");
        vioCaseStr.append("Subject        : ").append(violation.getSubject()).append("\n");
        vioCaseStr.append("Date Reported  : ").append(dateReported).append("\n");
        vioCaseStr.append("Status         : ").append(status).append("\n");
        vioCaseStr.append("Evidence Notes : ").append(evidenceNotes == null || evidenceNotes.isEmpty() ? "None recorded" : evidenceNotes).append("\n");

        if (sanction != null) {
            vioCaseStr.append("Sanction       : ").append(sanction).append("\n"); 
        } else {
            vioCaseStr.append("Sanction       : Sanction not recorded\n");
        }

        vioCaseStr.append("Remarks        : ").append(remarks == null || remarks.isEmpty() ? "None" : remarks);

        return vioCaseStr.toString();
    }

}
