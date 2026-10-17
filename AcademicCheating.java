public class AcademicCheating extends Violation {
    private boolean unauthorizedMaterialsUsed;
    private boolean collusionInvolved;

    /**
    Parameterized constructor for initializing Academic Cheating violation parameters.
        @param code                       Violation code.
        @param title                      Short title of violation.
        @param description                Detailed description.
        @param unauthorizedMaterialsUsed  True if forbidden materials were present/used.
        @param collusionInvolved          True if multiple individuals colluded.
     */
    public AcademicCheating(String code, String title, String description,
                            boolean unauthorizedMaterialsUsed, boolean collusionInvolved) {
        super(code, title, description);
        this.unauthorizedMaterialsUsed = unauthorizedMaterialsUsed;
        this.collusionInvolved = collusionInvolved;
    }

    /**
     Calculates severity score based on cheating specifics.
     Base severity is 2, increased by factor weights depending on materials and collusion.
     @return Integer severity score (range 2 - 5).
     */
    @Override
    public int calculateSeverityLevel() {
        int severity = 2; 
        if (unauthorizedMaterialsUsed) {
            severity += 2;
        }
        if (collusionInvolved) {
            severity += 1;
        }
        return Math.min(severity, 5); 
    }

    public boolean isUnauthorizedMaterialsUsed() {
        return unauthorizedMaterialsUsed;
    }

    public void setUnauthorizedMaterialsUsed(boolean unauthorizedMaterialsUsed) {
        this.unauthorizedMaterialsUsed = unauthorizedMaterialsUsed;
    }

    public boolean isCollusionInvolved() {
        return collusionInvolved;
    }

    public void setCollusionInvolved(boolean collusionInvolved) {
        this.collusionInvolved = collusionInvolved;
    }

    @Override
    public String toCSV() {
        return String.format("CHEATING,%s,%s,%s,%b,%b",
                getCode(), getTitle(), getDescription(), unauthorizedMaterialsUsed, collusionInvolved);
    }
}