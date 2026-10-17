import java.util.ArrayList;
import java.util.List;

/**
 * Primary manager class providing record tracking, updates, queries,
 * and data synchronization with persistent CSV storage.
 */
public class ViolationTracker {

    private List<Student> students;
    private List<ViolationCase> cases;

    private static final String STUDENTS_FILE = "DATA/students.csv";
    private static final String CASES_FILE = "DATA/cases.csv";

    /*Constructor initializing tracker state and loading saved data files.*/
    public ViolationTracker() {
        this.students = new ArrayList<>();
        this.cases = new ArrayList<>();
        loadAllData();
    }

    /* Loads stored data from CSV files. */
    public void loadAllData() {
        this.students = FileManager.loadStudents(STUDENTS_FILE);
        this.cases = FileManager.loadCases(CASES_FILE, this.students);
    }

    public void saveAllData() {
        FileManager.saveStudents(STUDENTS_FILE, students);
        FileManager.saveCases(CASES_FILE, cases);
    }

    public void addStudent(Student student) {
        if (student != null && getStudentById(student.getStudentId()) == null) {
            students.add(student);
            saveAllData();
        }
    }

    public Student getStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    public void addCase(ViolationCase vCase) {
        if (vCase != null) {
            cases.add(vCase);
            saveAllData();
        }
    }

    public ViolationCase getCaseById(String caseId) {
        for (ViolationCase c : cases) {
            if (c.getCaseId().equalsIgnoreCase(caseId)) {
                return c;
            }
        }
        return null;
    }

    public List<ViolationCase> getAllCases() {
        return cases;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean updateCaseStatus(String caseId, boolean isResolved, Sanction newSanction) {
        ViolationCase vCase = getCaseById(caseId);
        if (vCase != null) {
            vCase.setResolved(isResolved);
            if (newSanction != null) {
                vCase.setSanction(newSanction);
            }
            saveAllData();
            return true;
        }
        return false;
    }
}