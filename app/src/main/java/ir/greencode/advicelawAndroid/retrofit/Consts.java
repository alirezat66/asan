package ir.greencode.advicelawAndroid.retrofit;

public class Consts {
    public static String Key = "_bl4ck0ut_2F4162786374546C5677454F4A4E415359";
    public static String Base_Url = "http://142.93.136.242/api/v1/";
    public static String URL_API = "http://asan.direct/";
    //  public static String URL_API = "http://dev.coloan.ir/coloanServerSide/index.php/";
    public static String token = "";

    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
/*
    public static void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) AppController.getCurrentActivity().getSystemService("input_method");
        View view = AppController.getCurrentActivity().getCurrentFocus();
        if (view == null) {
            view = new View(AppController.getCurrentActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }*/
}
