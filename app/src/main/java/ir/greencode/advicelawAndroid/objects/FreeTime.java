package ir.greencode.advicelawAndroid.objects;

public class FreeTime {
    int timeId;
    int shift;
    String startTime;
    String endTime;
    String dayOfWeek;
    int blockId;

    public int getBlockId() {
        return blockId;
    }

    public int getTimeId() {
        return timeId;
    }

    public int getShift() {
        return shift;
    }

    public String getStartTime() {
        String[]part = startTime.split(":");
        return part[0]+":"+part[1];
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
