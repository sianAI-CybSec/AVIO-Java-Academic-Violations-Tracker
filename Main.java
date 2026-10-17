import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ViolationTracker vtracker = new ViolationTracker("DATA/students.csv","DATA/cases.csv");
        vtracker.loadData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = input.nextLine().trim();

            try{
                switch(choice){
                    case "1": 
                        addStudentMenu(input,vtracker);
                        break;
                    case "2":
                        viewAllStudentsMenu(vtracker);
                        break;
                    case "3":
                        addCaseMenu(input,vtracker);
                        break;
                    case "4":
                        viewAllCasesMenu(vtracker);
                        break;
                    case "5":
                        resolveCaseMenu(input, vtracker);
                        break;
                    case "6":
                        searchByStudentMenu(input,vtracker);
                        break;
                    case "7":
                        archiveCaseMenu(input,vtracker);
                        break;
                    case "8":
                        statisticsMenu(vtracker);
                        break;
                    case "9":
                        vtracker.saveData();
                        System.out.println("Data saved.");  
                        break;
                    case "10":
                        vtracker.saveData();
                        System.out.println("Data saved. Goodbye!");
                        running = false;
                    default:
                        System.out.println("Invalid choice. Enter 1-10.");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
        input.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== ACADEMIC INTEGRITY VIOLATION TRACKER =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Add Violation Case");
        System.out.println("4. View All Cases");
        System.out.println("5. Resolve a Case");
        System.out.println("6. Search Cases by Student ID");
        System.out.println("7. Archive (Delete) a Case");
        System.out.println("8. View Total/Resolved/Open Case Counts");
        System.out.println("9. Save Data");
        System.out.println("10. Exit");
        System.out.print("Choose: ");
    }
 
    // ---------- 1. Add Student ----------
    private static void addStudentMenu(Scanner sc, ViolationTracker tracker) {
        System.out.print("Student ID (8 digits): ");
        String id = sc.nextLine().trim();
        if (!id.matches("\\d{8}")) {
            System.out.println("Invalid ID. Must be exactly 8 digits.");
            return;
        }
        if (tracker.findStudentById(id) != null) {
            System.out.println("A student with that ID already exists.");
            return;
        }
 
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Program: ");
        String program = sc.nextLine().trim();
        System.out.print("HAU Email: ");
        String email = sc.nextLine().trim();
        System.out.print("School Year (e.g. 2026-2027): ");
        String schoolYear = sc.nextLine().trim();
 
        if (name.isEmpty() || program.isEmpty() || email.isEmpty() || schoolYear.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }
 
        tracker.addStudent(new Student(id, name, program, email, schoolYear));
        System.out.println("Student added.");
    }
 
    // ---------- 2. View All Students ----------
    private static void viewAllStudentsMenu(ViolationTracker tracker) {
        List<Student> students = tracker.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students recorded.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);   // calls Student.toString()
        }
    }
 
    // ---------- 3. Add Violation Case ----------
    private static void addCaseMenu(Scanner sc, ViolationTracker tracker) {
        System.out.print("Student ID: ");
        Student student = tracker.findStudentById(sc.nextLine().trim());
        if (student == null) {
            System.out.println("Student not found. Add the student first.");
            return;
        }
 
        System.out.print("Case ID (e.g. AC-2026-001): ");
        String caseId = sc.nextLine().trim();
        if (caseId.isEmpty() || tracker.findCaseById(caseId) != null) {
            System.out.println("Case ID is empty or already exists.");
            return;
        }
 
        System.out.print("Violation Code (IPV, AC-PAM, AC-C1, AC-C2, AC-P, AC-ED, AC-OC, AC-AI): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (!tracker.isValidViolationCode(code)) {
            System.out.println("Invalid violation code.");
            return;
        }
 
        LocalDate dateOfViolation = readDate(sc, "Date of Violation (YYYY-MM-DD): ");
        if (dateOfViolation == null) return;
 
        System.out.print("Subject: ");
        String subject = sc.nextLine().trim();
 
        // Polymorphism: Violation reference, subclass object chosen by code
        Violation violation;
        if (code.equals("IPV")) {
            System.out.print("Description of the work involved: ");
            String work = sc.nextLine().trim();
            violation = new IntellectualPropertyViolation(code, dateOfViolation, subject, work);
        } else {
            System.out.print("Assessment type (e.g. Midterm Exam): ");
            String assessment = sc.nextLine().trim();
            violation = new AcademicCheating(code, dateOfViolation, subject, assessment);
        }
 
        ViolationCase c = new ViolationCase(caseId, student, violation, LocalDate.now());
 
        System.out.print("Evidence notes: ");
        c.setEvidenceNotes(sc.nextLine().trim());
 
        tracker.addViolationCase(c);
        System.out.println("Case recorded with status OPEN.");
    }
 
    // ---------- 4. View All Cases ----------
    private static void viewAllCasesMenu(ViolationTracker tracker) {
        List<ViolationCase> cases = tracker.getAllCases();
        if (cases.isEmpty()) {
            System.out.println("No cases recorded.");
            return;
        }
        for (ViolationCase c : cases) {
            System.out.println("------------------------------");
            System.out.println(c);   // calls ViolationCase.toString()
        }
    }
 
    // ---------- 5. Resolve a Case ----------
    private static void resolveCaseMenu(Scanner sc, ViolationTracker tracker) {
        System.out.print("Enter Case ID: ");
        String caseId = sc.nextLine().trim();
 
        System.out.print("Sanction Code (VR, U, WW, WA, CS, S, EC, EX): ");
        String code = sc.nextLine().trim().toUpperCase();
 
        System.out.print("Sanction details (hours, duration, deadline): ");
        String details = sc.nextLine().trim();
 
        System.out.print("Remarks: ");
        String remarks = sc.nextLine().trim();
 
        // The sanction always comes from the user. The system never chooses it.
        Sanction sanction = new Sanction(code, LocalDate.now(), details);
        tracker.resolveViolationCase(caseId, sanction, remarks);
    }
 
    // ---------- 6. Search by Student ID ----------
    private static void searchByStudentMenu(Scanner sc, ViolationTracker tracker) {
        System.out.print("Student ID: ");
        List<ViolationCase> results = tracker.searchCasesByStudentId(sc.nextLine().trim());
        if (results.isEmpty()) {
            System.out.println("No cases found for that student.");
            return;
        }
        for (ViolationCase c : results) {
            System.out.println("------------------------------");
            System.out.println(c);
        }
    }
 
    // ---------- 7. Archive a Case ----------
    private static void archiveCaseMenu(Scanner sc, ViolationTracker tracker) {
        System.out.print("Case ID to archive: ");
        String caseId = sc.nextLine().trim();
 
        System.out.print("Are you sure you want to archive case " + caseId + "? [Y/N]: ");
        if (!sc.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("Cancelled.");
            return;
        }
 
        if (tracker.deleteViolationCase(caseId)) {
            System.out.println("Case archived.");
        } else {
            System.out.println("Case not found.");
        }
    }
 
    // ---------- 8. Statistics ----------
    private static void statisticsMenu(ViolationTracker tracker) {
        System.out.println("Total cases   : " + tracker.getTotalCases());
        System.out.println("Open cases    : " + tracker.searchCasesByStatus("OPEN").size());
        System.out.println("Resolved cases: " + tracker.searchCasesByStatus("RESOLVED").size());
        System.out.println("Archived cases: " + tracker.searchCasesByStatus("ARCHIVED").size());
    }
 
    // ---------- Helper: safe date input ----------
    private static LocalDate readDate(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            return LocalDate.parse(sc.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Use the format YYYY-MM-DD.");
            return null;
        }
    }
}
