public enum Sanction {
    // Sanction Constants
    VERBAL_REPRIMAND("Verbal Reprimand"),
    WRITTEN_WARNING("Written Warning"),
    WRITTEN_APOLOGY("Written Apology"),
    UNDERTAKING("Undertaking"),
    COMMUNITY_SERVICE("Community Service"),
    SUSPENSION("Suspension"),
    EXCLUSION("Exclusion"),
    EXPULSION("Expulsion");

    /* readable display label for the sanction. */
    private final String label;

    Sanction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Sanction fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return WRITTEN_WARNING;
        }
        for (Sanction s : Sanction.values()) {
            if (s.name().equalsIgnoreCase(text.trim()) || s.label.equalsIgnoreCase(text.trim())) {
                return s;
            }
        }
        return WRITTEN_WARNING;
    }
}