package ir.greencode.advicelawAndroid.objects;

public class Course {
    String title;
    String description;
    String docSource;
    long startDate;
    long endDate;
    int id;

    public Course(String title, String desc, String source) {
        this.title = title;
        this.description = desc;
        this.docSource = source;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return description;
    }

    public String getSource() {
        return docSource;
    }

    public String getDescription() {
        return description;
    }

    public String getDocSource() {
        return docSource;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }
}
