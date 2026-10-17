public abstract class Violation {
    private String code;
    private String title;
    private String description;

    /**
     Parameterized constructor for initializing base violation attributes.
     @param code        Violation code or identifier.
     @param title       Title or name of the violation.
     @param description Detailed description of the event.
     */
    public Violation(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    /**
     Abstract method that must be implemented by concrete subclasses to evaluate
     and calculate the numerical or categorical severity level of the violation.
     Demonstrates Polymorphism.
     @return Calculated severity score or rating.
     */
    public abstract int calculateSeverityLevel();

    public abstract String toCSV();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s (Severity: %d)", code, title, description, calculateSeverityLevel());
    }
}