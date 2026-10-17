
/**
 * Represents a student in the AVIO Academic Violations Tracker.
 * Stores student demographic and identification data such as ID, name, email, and department.
 * 
 * Demonstrates Object-Oriented Programming (OOP) Encapsulation by keeping fields private
 * and providing controlled access via public getters and setters.
 */
public class Student {
    private String studentId;
    private String name;
    private String email;
    private String department;

    public Student() {
        this.studentId = "";
        this.name = "";
        this.email = "";
        this.department = "";
    }

    public Student(String studentId, String name, String email, String department) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%s", studentId, name, email, department);
    }

    public static Student fromCSV(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        String[] parts = csvLine.split(",");
        if (parts.length < 4) {
            return null;
        }
        return new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
    }

    @Override
    public String toString() {
        return String.format("Student ID: %s | Name: %s | Email: %s | Department: %s",
                studentId, name, email, department);
    }
}