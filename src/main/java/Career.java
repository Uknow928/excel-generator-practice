public class Career {
    private String workPeriod;
    private String companyName;
    private String jobTitle;
    private String employmentYears;

    public Career(String workPeriod, String companyName, String jobTitle, String employmentYears) {
        this.workPeriod = workPeriod;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.employmentYears = employmentYears;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmploymentYears() {
        return employmentYears;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setEmploymentYears(String employmentYears) {
        this.employmentYears = employmentYears;
    }
}
