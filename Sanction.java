/**
 * Represents a disciplinary sanction imposed on a case.
 */
public class Sanction {
    private String sanctionType;
    private String dateImposed;
    private String details;

    public Sanction(String sanctionType, String dateImposed, String details) {
        this.sanctionType = sanctionType;
        this.dateImposed = dateImposed;
        this.details = details;
    }

    public String getSanctionType() { return sanctionType; }
    public String getDateImposed() { return dateImposed; }
    public String getDetails() { return details; }

    public String toCSV() {
        return sanctionType + "|" + dateImposed + "|" + details;
    }

    public static Sanction fromCSV(String text) {
        if (text == null || text.trim().isEmpty() || text.equalsIgnoreCase("NONE")) {
            return null;
        }
        String[] parts = text.split("\\|");
        if (parts.length < 3) {
            return new Sanction(parts[0], "N/A", "N/A");
        }
        return new Sanction(parts[0], parts[1], parts[2]);
    }

    @Override
    public String toString() {
        return sanctionType + " (Imposed: " + dateImposed + ", Details: " + details + ")";
    }
}