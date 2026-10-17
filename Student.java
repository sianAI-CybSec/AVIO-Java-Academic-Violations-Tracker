public class Student {
    private String studentId;
    private String name;
    private String program;
    private String hauEmail;
    private String schoolYear;
    private String dateOfBirth;
    private String guardianName;
    private String guardianPhoneNumber;

    public Student(String studentId, String name, String program, String hauEmail, String schoolYear, String dateOfBirth, String guardianName, String guardianPhoneNumber) {
        this.studentId = studentId;
        this.name = name;
        this.program = program;
        this.hauEmail = hauEmail;
        this.schoolYear = schoolYear;
        this.dateOfBirth = dateOfBirth;
        this.guardianName = guardianName;
        this.guardianPhoneNumber = guardianPhoneNumber;
    }

    public String toString() {
        return "Student ID: " + studentId + "\nStudent Name: " + name +
                "Program: " + program + "\nEmail: " + hauEmail +
                "School Year: " + schoolYear + "\nDate of Birth: " + dateOfBirth +
                "Guardian Name: " + guardianName + "\nGuardian Phone Number: " + guardianPhoneNumber;
    }

    public String toCSV() {
        return String.join(",", studentId, name, program, hauEmail, schoolYear, dateOfBirth, guardianName, guardianPhoneNumber);
    }

    public static Student fromCSV(String line) {
        String[] data = line.split(",", -1);

        if (data.length >= 8) {
            return new Student(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim(), data[6].trim(), data[7].trim());
        }

        throw new IllegalArgumentException("Invalid CSV Line for Student: " + line);
    }


    // Getters for Student Fields stuffz
    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public String getHauEmail() {
        return hauEmail;
    }

    public String getSchoolYear() {
        return schoolYear;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getGuardianPhoneNumber() {
        return guardianPhoneNumber;
    }


    // Student Fields Setters
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setHauEmail(String hauEmail) {
        this.hauEmail = hauEmail;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public void setGuardianPhoneNumber(String guardianPhoneNumber) {
        this.guardianPhoneNumber = guardianPhoneNumber;
    }
}
