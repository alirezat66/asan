package ir.greencode.advicelawAndroid.objects;

public class DocumentImg {
    String docTitle;
    String docSize;
    String docSource;

    public DocumentImg(String docTitle, String docSize, String docSource) {
        this.docTitle = docTitle;
        this.docSize = docSize;
        this.docSource = docSource;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public String getDocSize() {
        return docSize;
    }

    public String getDocSource() {
        return docSource;
    }
}
