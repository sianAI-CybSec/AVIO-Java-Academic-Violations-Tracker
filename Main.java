import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point and Interactive User Interface for AVIO Academic Violations Tracker.
 * Manages user console interaction, command processing, and data input validation.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ViolationTracker tracker = new ViolationTracker();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  AVIO - Academic Violations Tracker System");
        System.out.println("=================================================");

        boolean exit = false;
        while (!exit) {
            printMenu();
            System.out.print("Select an option (1-6): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    registerStudentUI();
                    break;
                case "2":
                    recordViolationCaseUI();
                    break;
                case "3":
                    listAllCasesUI();
                    break;
                case "4":
                    listAllStudentsUI();
                    break;
                case "5":
                    updateCaseStatusUI();
                    break;
                case "6":
                    exit = true;
                    System.out.println("Exiting AVIO System. All data persisted. Goodbye!");
                    break;
                default:
                    System.out.println("[!] Invalid option. Please enter a number between 1 and 6.\n");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println("1. Register New Student");
        System.out.println("2. Record New Violation Case");
        System.out.println("3. Display All Violation Cases");
        System.out.println("4. Display All Registered Students");
        System.out.println("5. Update Case Status / Sanction");
        System.out.println("6. Exit");
        System.out.println("-------------------------------------------");
    }

    private static void registerStudentUI() {
        System.out.println("\n--- Register Student ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        if (tracker.getStudentById(id) != null) {
            System.out.println("[!] A student with ID " + id + " already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Department: ");
        String dept = scanner.nextLine().trim();

        Student student = new Student(id, name, email, dept);
        tracker.addStudent(student);
        System.out.println("[✓] Student successfully registered in system!");
    }

    private static void recordViolationCaseUI() {
        System.out.println("\n--- Record Violation Case ---");
        System.out.print("Enter Case ID (e.g., CASE-101): ");
        String caseId = scanner.nextLine().trim();

        if (tracker.getCaseById(caseId) != null) {
            System.out.println("[!] Case ID already exists. Enter another Case ID (e.g., CASE-101).");
            return;
        }

        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        Student student = tracker.getStudentById(studentId);

        if (student == null) {
            System.out.println("[!] Student ID not found. Please register student first in system.");
            return;
        }

        System.out.println("Select Violation Type:");
        System.out.println("1. Academic Cheating");
        System.out.println("2. Intellectual Property / Plagiarism");
        System.out.print("Choice: ");
        String typeChoice = scanner.nextLine().trim();

        Violation violation = null;
        System.out.print("Enter Violation Code (e.g., V001): ");
        String code = scanner.nextLine().trim();
        System.out.print("Enter Short Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Description: ");
        String desc = scanner.nextLine().trim();

        if ("1".equals(typeChoice)) {
            System.out.print("Were unauthorized materials used? (true/false): ");
            boolean unauth = Boolean.parseBoolean(scanner.nextLine().trim());
            System.out.print("Was collusion involved? (true/false): ");
            boolean coll = Boolean.parseBoolean(scanner.nextLine().trim());
            violation = new AcademicCheating(code, title, desc, unauth, coll);
        } else if ("2".equals(typeChoice)) {
            System.out.print("Enter similarity percentage (0 - 100): ");
            double sim = 0.0;
            try {
                sim = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid percentage. Defaulting to 0.0");
            }
            violation = new IntellectualPropertyViolation(code, title, desc, sim);
        } else {
            System.out.println("[!] Invalid violation choice.");
            return;
        }

        System.out.println("Select Sanction:");
        int idx = 1;
        for (Sanction s : Sanction.values()) {
            System.out.println(idx + ". " + s.getLabel());
            idx++;
        }
        System.out.print("Sanction index: ");
        Sanction selectedSanction = Sanction.WRITTEN_WARNING;
        try {
            int sChoice = Integer.parseInt(scanner.nextLine().trim());
            if (sChoice >= 1 && sChoice <= Sanction.values().length) {
                selectedSanction = Sanction.values()[sChoice - 1];
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid choice. Defaulting to WRITTEN_WARNING.");
        }

        ViolationCase vCase = new ViolationCase(caseId, student, violation, LocalDate.now(), selectedSanction, false);
        tracker.addCase(vCase);
        System.out.println("[✓] Violation Case registered successfully.");
    }

    private static void listAllCasesUI() {
        System.out.println("\n--- All Recorded Violation Cases ---");
        List<ViolationCase> cases = tracker.getAllCases();
        if (cases.isEmpty()) {
            System.out.println("No violation cases found.");
        } else {
            for (ViolationCase c : cases) {
                System.out.println(c);
            }
        }
    }

    private static void listAllStudentsUI() {
        System.out.println("\n--- All Registered Students ---");
        List<Student> students = tracker.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private static void updateCaseStatusUI() {
        System.out.println("\n--- Update Case Status ---");
        System.out.print("Enter Case ID to update: ");
        String caseId = scanner.nextLine().trim();

        ViolationCase vCase = tracker.getCaseById(caseId);
        if (vCase == null) {
            System.out.println("[!] Case ID not found.");
            return;
        }

        System.out.print("Mark as resolved? (true/false): ");
        boolean resolved = Boolean.parseBoolean(scanner.nextLine().trim());

        System.out.println("Select New Sanction:");
        int idx = 1;
        for (Sanction s : Sanction.values()) {
            System.out.println(idx + ". " + s.getLabel());
            idx++;
        }
        System.out.print("Choice: ");
        Sanction selectedSanction = vCase.getSanction();
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= Sanction.values().length) {
                selectedSanction = Sanction.values()[choice - 1];
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Keeping current sanction.");
        }

        boolean updated = tracker.updateCaseStatus(caseId, resolved, selectedSanction);
        if (updated) {
            System.out.println("[✓] Case successfully updated.");
        } else {
            System.out.println("[!] Failed to update case.");
        }
    }
}