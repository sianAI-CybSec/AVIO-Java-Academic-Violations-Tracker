import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages reading and writing Student and ViolationCase data to/from files.
 */
public class FileManager {
    private String studentFilePath;
    private String caseFilePath;

    public FileManager() {
        this.studentFilePath = "DATA/students.csv";
        this.caseFilePath = "DATA/cases.csv";
        ensureDirectoryExists();
    }

    public FileManager(String studentFilePath, String caseFilePath) {
        this.studentFilePath = studentFilePath;
        this.caseFilePath = caseFilePath;
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        File dir = new File("DATA");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(studentFilePath);
        if (!file.exists()) return students;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    students.add(Student.fromCSV(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading student file: " + e.getMessage());
        }
        return students;
    }

    public void saveStudents(List<Student> students) {
        ensureDirectoryExists();
        try (PrintWriter pw = new PrintWriter(new FileWriter(studentFilePath))) {
            for (Student s : students) {
                pw.println(s.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error writing student file: " + e.getMessage());
        }
    }

    public List<ViolationCase> loadCases(List<Student> students) {
        List<ViolationCase> cases = new ArrayList<>();
        File file = new File(caseFilePath);
        if (!file.exists()) return cases;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {

                    String[] parts = line.split(";");
                    if (parts.length >= 8) {
                        String caseId = parts[0];
                        String studentId = parts[1];
                        Student student = null;
                        for (Student s : students) {
                            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                                student = s;
                                break;
                            }
                        }
                        // Parse violation type and details
                        String violationData = parts[2];
                        Violation violation = null;
                        String[] vParts = violationData.split(",");
                        if (vParts.length >= 4) {
                            String code = vParts[0];
                            String date = vParts[1];
                            String subject = vParts[2];
                            String extra = vParts[3];
                            if (code.equals("IPV")) {
                                violation = new IntellectualPropertyViolation(code, date, subject, extra);
                            } else {
                                violation = new AcademicCheating(code, date, subject, extra);
                            }
                        }
                        // Parse sanction if present
                        Sanction sanction = null;
                        if (!parts[3].equals("null")) {
                            String[] sParts = parts[3].split(",");
                            if (sParts.length >= 3) {
                                sanction = new Sanction(sParts[0], sParts[1], sParts[2]);
                            }
                        }

                        String status = parts[4];
                        String dateReported = parts[5];
                        String evidenceNotes = parts[6];
                        String remarks = parts[7];

                        if (student != null && violation != null) {
                            ViolationCase vCase = new ViolationCase(caseId, student, violation, sanction, status, dateReported, evidenceNotes, remarks);
                            cases.add(vCase);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading cases file: " + e.getMessage());
        }
        return cases;
    }

    public void saveCases(List<ViolationCase> cases) {
        ensureDirectoryExists();
        try (PrintWriter pw = new PrintWriter(new FileWriter(caseFilePath))) {
            for (ViolationCase c : cases) {
                pw.println(c.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error writing cases file: " + e.getMessage());
        }
    }
}