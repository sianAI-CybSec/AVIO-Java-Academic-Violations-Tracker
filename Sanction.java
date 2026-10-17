public class Sanction {
    private String sanctionType;
    private String dateImposed;
    private String details;

    public Sanction(String sanctionType, String dateImposed, String details){
        this.sanctionType = sanctionType;
        this.dateImposed = dateImposed;
        this.details = details;
    }

    public String getSanctionType(){return sanctionType;}
    public String getDateImposed(){return dateImposed;}
    public String getDetails(){return details;}

    public String toCSV(){
        FileManager fm = new FileManager();
        String csvResult = sanctionType + "," + dateImposed + "," + fm.escapeCsv(details);
        return csvResult;
    }

    public String toString(){
        String sanctionStr = String.format(
            "Sanction: %s\nDate Imposed: %s\nDetails: %s\n",sanctionType,dateImposed,details
        );
        return sanctionStr;
    }
}
