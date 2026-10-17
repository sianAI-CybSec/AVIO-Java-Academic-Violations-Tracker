import java.util.*;

public class ViolationTracker {
    private FileManager fileManager = new FileManager();
    private List<Student> students = fileManager.loadStudents();
    private List<ViolationCase> cases = fileManager.loadCases(students);

    public void addStudent(Student student) {
        students.add(student);
        fileManager.saveStudents(students);
    }

    public Student findStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }

    public void addViolationCase(ViolationCase violationCase) {
        cases.add(violationCase);
        fileManager.saveCases(cases);
    }

    public ViolationCase findCaseById(String caseId) {
        for (ViolationCase c : cases) {
            if (c.getCaseId().equals(caseId)) {
                return c;
            }
        }
        return null;
    }

    public void resolveViolationCase(String caseId, Sanction sanction, String remarks) {
        ViolationCase c = findCaseById(caseId);
        if (c != null) {
            c.resolve(sanction, remarks);
            fileManager.saveCases(cases);
        }
    }

    public boolean deleteViolationCase(String caseId) {
        ViolationCase c = findCaseById(caseId);
        if (c == null) {
            return false;
        }
        cases.remove(c);
        fileManager.saveCases(cases);
        return true;
    }

    public List<ViolationCase> searchCasesByStudentId(String studentId) {
        List<ViolationCase> results = new ArrayList<ViolationCase>();
        for (ViolationCase c : cases) {
            if (c.getStudent().getStudentId().equals(studentId)) {
                results.add(c);
            }
        }
        return results;
    }

    public List<ViolationCase> searchCasesByStatus(String status) {
        List<ViolationCase> results = new ArrayList<ViolationCase>();
        for (ViolationCase c : cases) {
            if (c.getStatus().equalsIgnoreCase(status)) {
                results.add(c);
            }
        }
        return results;
    }

    public int getTotalCases() {
        return cases.size();
    }

    public boolean isValidViolationCode(String code) {
        return code != null && !code.trim().equals("");
    }

    public boolean isValidSanctionCode(String code) {
        return code != null && !code.trim().equals("");
    }
}