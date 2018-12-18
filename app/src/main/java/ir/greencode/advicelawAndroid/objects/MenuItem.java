package ir.greencode.advicelawAndroid.objects;

public class MenuItem {
    int position;
    String title;

    public MenuItem(int position, String title) {
        this.position = position;
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }
}
