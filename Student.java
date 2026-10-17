/**
 * Represents a student with personal and academic details.
 */
public class Student {
    private String studentId;
    private String name;
    private String program;
    private String hauEmail;
    private String schoolYear;
    private String dateOfBirth;
    private String guardianName;
    private String guardianPhoneNumber;

    public Student(String studentId, String name, String program, String hauEmail, 
                   String schoolYear, String dateOfBirth, String guardianName, String guardianPhoneNumber) {
        this.studentId = studentId;
        this.name = name;
        this.program = program;
        this.hauEmail = hauEmail;
        this.schoolYear = schoolYear;
        this.dateOfBirth = dateOfBirth;
        this.guardianName = guardianName;
        this.guardianPhoneNumber = guardianPhoneNumber;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public String getHauEmail() { return hauEmail; }
    public void setHauEmail(String hauEmail) { this.hauEmail = hauEmail; }

    public String getSchoolYear() { return schoolYear; }
    public void setSchoolYear(String schoolYear) { this.schoolYear = schoolYear; }

    public String getDateOfBirth() { return dateOfBirth; }

    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }

    public String getGuardianPhoneNumber() { return guardianPhoneNumber; }
    public void setGuardianPhoneNumber(String guardianPhoneNumber) { this.guardianPhoneNumber = guardianPhoneNumber; }

    public String toCSV() {
        return studentId + "," + name + "," + program + "," + hauEmail + "," + 
               schoolYear + "," + dateOfBirth + "," + guardianName + "," + guardianPhoneNumber;
    }

    public static Student fromCSV(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",");
        if (parts.length < 8) return null;
        return new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                           parts[4].trim(), parts[5].trim(), parts[6].trim(), parts[7].trim());
    }

    @Override
    public String toString() {
        return "ID: " + studentId + " | Name: " + name + " | Program: " + program + " | Email: " + hauEmail;
    }
}