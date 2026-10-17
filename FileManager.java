import java.io.*;
import java.util.*;

public class FileManager {
    private String studentFilePath = "students.csv";
    private String caseFilePath = "cases.csv";

    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<Student>();
        try {
            File file = new File(studentFilePath);
            if (!file.exists()) {
                return students;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.fromCSV(line));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading students: " + e.getMessage());
        }
        return students;
    }

    public void saveStudents(List<Student> students) {
        try {
            FileWriter writer = new FileWriter(studentFilePath);
            for (Student s : students) {
                writer.write(s.toCSV() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public List<ViolationCase> loadCases(List<Student> knownStudents) {
        List<ViolationCase> cases = new ArrayList<ViolationCase>();
        try {
            File file = new File(caseFilePath);
            if (!file.exists()) {
                return cases;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);
                Student student = null;
                for (Student s : knownStudents) {
                    if (s.getStudentId().equals(p[1])) {
                        student = s;
                    }
                }
                Violation violation;
                if (p[2].equals("ACADEMIC")) {
                    violation = new AcademicCheating(p[3], p[4], p[5], p[6]);
                } else {
                    violation = new IntellectualPropertyViolation(p[3], p[4], p[5], p[6]);
                }
                Sanction sanction = null;
                if (!p[7].equals("")) {
                    sanction = new Sanction(p[7], p[8], p[9]);
                }
                cases.add(new ViolationCase(p[0], student, violation, sanction, p[10], p[11], p[12], p[13]));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading cases: " + e.getMessage());
        }
        return cases;
    }

    public void saveCases(List<ViolationCase> cases) {
        try {
            FileWriter writer = new FileWriter(caseFilePath);
            for (ViolationCase c : cases) {
                writer.write(c.toCSV() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving cases: " + e.getMessage());
        }
    }
}