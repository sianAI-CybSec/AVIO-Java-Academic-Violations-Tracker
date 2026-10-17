public class IntellectualPropertyViolation extends Violation {
    private double similarityPercentage;

    /**
     Constructor for Intellectual Property Violations.
     @param code                 Violation unique code.
     @param title                Title of offense.
     @param description          Full detailed narrative of the case.
     @param similarityPercentage Similarity score percentage.
     */
    public IntellectualPropertyViolation(String code, String title, String description, double similarityPercentage) {
        super(code, title, description);
        this.similarityPercentage = similarityPercentage;
    }

    /**
     Calculates the severity score based on similarity percentage.
     @return Integer severity score between 1 and 5.
     */
    @Override
    public int calculateSeverityLevel() {
        if (similarityPercentage >= 75.0) {
            return 5;
        } else if (similarityPercentage >= 50.0) {
            return 4;
        } else if (similarityPercentage >= 25.0) {
            return 3;
        } else if (similarityPercentage > 0.0) {
            return 2;
        }
        return 1;
    }

    public double getSimilarityPercentage() {
        return similarityPercentage;
    }

    public void setSimilarityPercentage(double similarityPercentage) {
        this.similarityPercentage = similarityPercentage;
    }

    @Override
    public String toCSV() {
        return String.format("IP_VIOLATION,%s,%s,%s,%.2f",
                getCode(), getTitle(), getDescription(), similarityPercentage);
    }
}