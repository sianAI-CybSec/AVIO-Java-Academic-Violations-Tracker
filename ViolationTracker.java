import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Handles operations on students and violation cases.
 */
public class ViolationTracker {

    private List<Student> students;
    private List<ViolationCase> cases;
    private FileManager fileManager;

    public ViolationTracker() {
        this.fileManager = new FileManager();
        this.students = fileManager.loadStudents();
        this.cases = fileManager.loadCases(this.students);
    }

    public void addStudent(Student student) {
        students.add(student);
        fileManager.saveStudents(students);
        System.out.println("Student added successfully.");
    }

    public Student findStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    public void addViolationCase(ViolationCase caseObj) {
        cases.add(caseObj);
        fileManager.saveCases(cases);
        System.out.println("Violation case recorded successfully.");
    }

    public ViolationCase findCaseById(String caseId) {
        for (ViolationCase c : cases) {
            if (c.getCaseId().equalsIgnoreCase(caseId)) {
                return c;
            }
        }
        return null;
    }

    public void resolveViolationCase(String caseId, Sanction sanction, String remarks) {
        ViolationCase vCase = findCaseById(caseId);
        if (vCase != null) {
            vCase.resolve(sanction, remarks);
            fileManager.saveCases(cases);
            System.out.println("Case " + caseId + " resolved successfully.");
        } else {
            System.out.println("Case not found.");
        }
    }

    public boolean deleteViolationCase(String caseId) {
        ViolationCase target = findCaseById(caseId);
        if (target != null) {
            cases.remove(target);
            fileManager.saveCases(cases);
            System.out.println("Case deleted successfully.");
            return true;
        }
        System.out.println("Case not found.");
        return false;
    }

    public List<ViolationCase> searchCasesByStudentId(String studentId) {
        List<ViolationCase> result = new ArrayList<>();
        for (ViolationCase c : cases) {
            if (c.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<ViolationCase> searchCasesByStatus(String status) {
        List<ViolationCase> result = new ArrayList<>();
        for (ViolationCase c : cases) {
            if (c.getStatus().equalsIgnoreCase(status)) {
                result.add(c);
            }
        }
        return result;
    }

    public int getTotalCases() {
        return cases.size();
    }

    public void displayStudentFiles(){
        String studentCSV = "DATA/students.csv";
        String line = "";
        String separator = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(studentCSV))){
            while ((line = br.readLine()) != null){
                String[] information = line.split(separator);
                for (String info : information){
                    System.out.print(info + " | ");
                }
                System.out.println();
            }
        } catch (IOException e){
            System.out.println("[!] Student records file not found.");
            e.printStackTrace();
        }
    }

    public void displayViolationCases(){
        String violationsCSV = "DATA/cases.csv";
        String line = "";
        String separator = ",|;";

        try(BufferedReader br = new BufferedReader(new FileReader(violationsCSV))){
            while((line = br.readLine()) != null){
                String[] violations = line.split(separator);
                for (String vioCase : violations){
                    System.out.print(vioCase + " | ");
                }
                System.out.println();
            }
        } catch (IOException e){
            System.out.println("[!] Student records file not found.");
            e.printStackTrace();
        }
    }

    public boolean isValidViolationCode(String code) {
        if (code == null) return false;
        String upper = code.trim().toUpperCase();
        return upper.equals("IPV") ||
               upper.equals("AC-PAM") ||
               upper.equals("AC-C1") ||
               upper.equals("AC-C2") ||
               upper.equals("AC-P") ||
               upper.equals("AC-ED") ||
               upper.equals("AC-OC") ||
               upper.equals("AC-AI");
    }

    public boolean isValidSanctionCode(String code) {
        if (code == null) return false;
        String lower = code.trim().toLowerCase();
        return lower.contains("warning") ||
               lower.contains("verbal") ||
               lower.contains("undertaking") ||
               lower.contains("apology") ||
               lower.contains("community service") ||
               lower.contains("suspension") ||
               lower.contains("exclusion") ||
               lower.contains("expulsion") ||
               lower.equals("a") || lower.equals("b") || lower.equals("c") ||
               lower.equals("d") || lower.equals("e") || lower.equals("f") ||
               lower.equals("g") || lower.equals("h");
    }
}