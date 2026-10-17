import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Academic Violations Tracker (AVIO) application.
 * Manages user interface and console menus.
 */
public class AVIO_Main {

    public static void main(String[] args) {
        ViolationTracker tracker = new ViolationTracker();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                // Register a new Student
                System.out.print("Enter Student ID: ");
                String id = scanner.nextLine().trim();
                System.out.print("Enter Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Enter Program: ");
                String program = scanner.nextLine().trim();
                System.out.print("Enter HAU Email: ");
                String email = scanner.nextLine().trim();
                System.out.print("Enter School Year: ");
                String schoolYear = scanner.nextLine().trim();
                System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                String dob = scanner.nextLine().trim();
                System.out.print("Enter Guardian Name: ");
                String guardianName = scanner.nextLine().trim();
                System.out.print("Enter Guardian Phone Number: ");
                String guardianPhone = scanner.nextLine().trim();

                Student student = new Student(id, name, program, email, schoolYear, dob, guardianName, guardianPhone);
                tracker.addStudent(student);

            } else if (choice.equals("2")) {
                // Add a Violation Case
                System.out.print("Enter Case ID: ");
                String caseId = scanner.nextLine().trim();

                System.out.print("Enter Student ID: ");
                String studentId = scanner.nextLine().trim();
                Student student = tracker.findStudentById(studentId);

                if (student == null) {
                    System.out.println("Error: Student not found! Please register student first.");
                    continue;
                }

                System.out.println("\n--- AVAILABLE VIOLATION CODES ---");
                System.out.println("Intellectual Property Violation:");
                System.out.println("  IPV - Violation of HAU Intellectual Property Policies");
                System.out.println("Academic Cheating Violations:");
                System.out.println("  AC-PAM - Unauthorized possession of academic materials relative to examinations");
                System.out.println("  AC-C1  - Copying from another's exam paper");
                System.out.println("  AC-C2  - Allowing to Copy another from own exam paper");
                System.out.println("  AC-P   - Plagiarism");
                System.out.println("  AC-ED  - Unauthorized use of electronic devices during exam");
                System.out.println("  AC-OC  - Cheating during online classes");
                System.out.println("  AC-AI  - Unauthorized AI use to complete academic requirements");
                System.out.println("---------------------------------");

                System.out.print("Enter Violation Code: ");
                String code = scanner.nextLine().trim().toUpperCase();

                if (!tracker.isValidViolationCode(code)) {
                    System.out.println("Error: Invalid violation code!");
                    continue;
                }

                System.out.print("Enter Date of Violation (YYYY-MM-DD): ");
                String dateOfViolation = scanner.nextLine().trim();
                System.out.print("Enter Subject: ");
                String subject = scanner.nextLine().trim();

                Violation violation = null;
                if (code.equals("IPV")) {
                    System.out.print("Enter Work Description: ");
                    String workDesc = scanner.nextLine().trim();
                    violation = new IntellectualPropertyViolation(code, dateOfViolation, subject, workDesc);
                } else {
                    System.out.print("Enter Assessment Type (Exam, Quiz, Assignment, etc.): ");
                    String assessType = scanner.nextLine().trim();
                    violation = new AcademicCheating(code, dateOfViolation, subject, assessType);
                }

                System.out.print("Enter Date Reported (YYYY-MM-DD): ");
                String dateReported = scanner.nextLine().trim();
                System.out.print("Enter Evidence Notes: ");
                String evidenceNotes = scanner.nextLine().trim();
                System.out.print("Enter Remarks: ");
                String remarks = scanner.nextLine().trim();

                ViolationCase vCase = new ViolationCase(caseId, student, violation, null, "PENDING", dateReported, evidenceNotes, remarks);
                tracker.addViolationCase(vCase);

            } else if (choice.equals("3")) {
                // Resolve Case
                System.out.print("Enter Case ID to resolve: ");
                String caseId = scanner.nextLine().trim();

                ViolationCase vCase = tracker.findCaseById(caseId);
                if (vCase == null) {
                    System.out.println("Error: Violation Case not found!");
                    continue;
                }

                System.out.println("\n--- AVAILABLE SANCTIONS ---");
                System.out.println("  a) Warning / Verbal Reprimand");
                System.out.println("  b) Undertaking");
                System.out.println("  c) Written Warning");
                System.out.println("  d) Written Apology");
                System.out.println("  e) Community Service");
                System.out.println("  f) Suspension");
                System.out.println("  g) Exclusion");
                System.out.println("  h) Expulsion");
                System.out.println("----------------------------");

                System.out.print("Enter Sanction Type (e.g., Warning / Verbal Reprimand, Written Warning, etc.): ");
                String sanctionType = scanner.nextLine().trim();

                if (!tracker.isValidSanctionCode(sanctionType)) {
                    System.out.println("Error: Invalid sanction type selected!");
                    continue;
                }

                System.out.print("Enter Date Imposed (YYYY-MM-DD): ");
                String dateImposed = scanner.nextLine().trim();
                System.out.print("Enter Sanction Details: ");
                String details = scanner.nextLine().trim();

                Sanction sanction = new Sanction(sanctionType, dateImposed, details);

                System.out.print("Enter Resolution Remarks: ");
                String remarks = scanner.nextLine().trim();

                tracker.resolveViolationCase(caseId, sanction, remarks);

            } else if (choice.equals("4")) {
                // Search Cases by Student ID
                System.out.print("Enter Student ID: ");
                String studentId = scanner.nextLine().trim();
                List<ViolationCase> results = tracker.searchCasesByStudentId(studentId);

                if (results.isEmpty()) {
                    System.out.println("No cases found for student ID: " + studentId);
                } else {
                    for (ViolationCase c : results) {
                        System.out.println(c.toString());
                    }
                }

            } else if (choice.equals("5")) {
                // Search Cases by Status
                System.out.print("Enter Status (PENDING / RESOLVED): ");
                String status = scanner.nextLine().trim();
                List<ViolationCase> results = tracker.searchCasesByStatus(status);

                if (results.isEmpty()) {
                    System.out.println("No cases found with status: " + status);
                } else {
                    for (ViolationCase c : results) {
                        System.out.println(c.toString());
                    }
                }

            } else if (choice.equals("6")) {
                // Delete Case
                System.out.print("Enter Case ID to delete: ");
                String caseId = scanner.nextLine().trim();
                tracker.deleteViolationCase(caseId);

            } else if (choice.equals("7")) {
                // Total Cases Count
                System.out.println("Total Recorded Cases: " + tracker.getTotalCases());

            } else if (choice.equals("8")) {
                running = false;
                System.out.println("Exiting system...");
                System.out.println("Laus Deo Semper!");

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    public static void printMenu() {
        System.out.println("\n=== ACADEMIC VIOLATIONS TRACKER (AVIO) ===");
        System.out.println("1. Register Student");
        System.out.println("2. Record Violation Case");
        System.out.println("3. Resolve Violation Case");
        System.out.println("4. Search Cases by Student ID");
        System.out.println("5. Search Cases by Status");
        System.out.println("6. Delete Violation Case");
        System.out.println("7. View Total Cases");
        System.out.println("8. Exit");
        System.out.println("==========================================");
    }
}