import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file reading and persistence operations for Students and Violation Cases in CSV format.
 * Prevents file corruption and handles missing directory/file scenarios gracefully.
 */
public class FileManager {

    public static boolean saveStudents(String filePath, List<Student> students) {
        ensureDirectoryExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Student student : students) {
                if (student != null) {
                    writer.write(student.toCSV());
                    writer.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("[Error] Failed to save student records: " + e.getMessage());
            return false;
        }
    }

    public static List<Student> loadStudents(String filePath) {
        List<Student> students = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromCSV(line);
                if (student != null) {
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.err.println("[Error] Failed to read students file: " + e.getMessage());
        }
        return students;
    }

    public static boolean saveCases(String filePath, List<ViolationCase> cases) {
        ensureDirectoryExists(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ViolationCase vCase : cases) {
                if (vCase != null) {
                    writer.write(vCase.toCSV());
                    writer.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("[Error] Failed to save violation cases: " + e.getMessage());
            return false;
        }
    }

    public static List<ViolationCase> loadCases(String filePath, List<Student> students) {
        List<ViolationCase> cases = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return cases;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(";");
                if (parts.length < 6) continue;

                String caseId = parts[0].trim();
                String studentId = parts[1].trim();
                String violationData = parts[2].trim();
                String dateStr = parts[3].trim();
                String sanctionStr = parts[4].trim();
                boolean resolved = Boolean.parseBoolean(parts[5].trim());

                // Find corresponding student reference
                Student studentRef = findStudentById(students, studentId);

                // Parse polymorphically created violation object
                Violation violation = parseViolation(violationData);

                LocalDate date;
                try {
                    date = LocalDate.parse(dateStr);
                } catch (DateTimeParseException e) {
                    date = LocalDate.now();
                }

                Sanction sanction = Sanction.fromString(sanctionStr);

                cases.add(new ViolationCase(caseId, studentRef, violation, date, sanction, resolved));
            }
        } catch (IOException e) {
            System.err.println("[Error] Failed to read cases file: " + e.getMessage());
        }
        return cases;
    }

    private static Violation parseViolation(String rawData) {
        String[] tokens = rawData.split(",");
        if (tokens.length < 5) return null;

        String type = tokens[0].trim();
        String code = tokens[1].trim();
        String title = tokens[2].trim();
        String desc = tokens[3].trim();

        if ("CHEATING".equalsIgnoreCase(type)) {
            boolean unauth = Boolean.parseBoolean(tokens[4].trim());
            boolean coll = tokens.length > 5 && Boolean.parseBoolean(tokens[5].trim());
            return new AcademicCheating(code, title, desc, unauth, coll);
        } else if ("IP_VIOLATION".equalsIgnoreCase(type)) {
            double sim = Double.parseDouble(tokens[4].trim());
            return new IntellectualPropertyViolation(code, title, desc, sim);
        }
        return null;
    }

    private static Student findStudentById(List<Student> students, String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    private static void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}