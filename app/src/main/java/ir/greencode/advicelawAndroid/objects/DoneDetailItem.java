package ir.greencode.advicelawAndroid.objects;

public class DoneDetailItem {
    String mainTitle;
    String secondTitle;
    String desc;

    public DoneDetailItem(String mainTitle, String secondTitle, String desc) {
        this.mainTitle = mainTitle;
        this.secondTitle = secondTitle;
        this.desc = desc;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public String getDesc() {
        return desc;
    }
}
