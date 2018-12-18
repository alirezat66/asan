package ir.greencode.advicelawAndroid.retrofit;

public enum MyMethods {
    Authentication("Authentication", 0),
    GetCats("GetCats", 1),
    GetSubCat("GetSubCat", 2),
    Login("Login", 3),
    GetActiveList("GetActiveList", 4),
    GetWaitedDetail("GetWaitedDetail", 5),
    InsertRequest("InsertRequest", 6),
    GetActivatedDetail("GetActivatedDetail", 7),
    AcceptRequest("AcceptRequest", 8),
    CancelReq("CancelReq", 9),
    ProfileInfo("ProfileInfo", 10),
    UpdateProfile("UpdateProfile", 11),
    GetDoneDetial("doneDetial", 12),
    CancelOffer("CancelOffer", 13),
    SetProfile("GetLoanUser", 7),
    AddDrug("addDrug", 8),
    AddUsage("addUsage", 9),
    GetDrugs("getDrugs", 10),
    PayByAccess("PayByAccess", 11),
    ConfirmUser("ConfirmUser", 12),
    GetPayByAccessList("GetPayByAccessList", 13),
    PrepareInvoice("PrepareInvoice", 14),
    GetPaymentDetail("GetPaymentDetail", 15),
    UpdateProfilePicture("UpdateProfilePicture", 16),
    UserInfo("UserInfo", 17),
    GetMyRequest("MyRequest", 18),
    SendMessage("SendMessage", 20),
    Upload("Upload", 21),
    GetUserStatistic("GetUserStatistics" , 22),
    CreateInvoiceOffline("CreateInvoiceOffline" , 23),
    UPDATECALL("updateCall", 24),
    GetCallState("CallState" , 25),
    SendGCMToken("sendGCMToken" , 26),
    Logout("logout" , 27)
    ,GetPaymentReport("PaymetReport" , 26)
    ,CreateLottery("CreateLottory" , 27)
    ,PrepareLottery("PrepareLottory" , 28)
    ,GetLotteryGeneral("GetLotteryGeneral" , 29)
    ,PrepareWithdrawal("PrepareWithdrawal" , 30)
    ,setTransactionResult("setTransactionResult" , 31)
    ,CreateWithdrawal("CreateWithdrawal" , 32)
    ,GetWithdrawalDetails("GetWithdrawalDetails" , 32)
    ,ResetPasswordSetNewPassword("ResetPasswordSetNewPassword" , 33)
    ,ResetPasswordActiveCode("ResetPasswordActiveCode" , 34)
    ,GetAlaets("GetAlarts" , 35)
    ,GetAlaetsDetail("GetAlartsDetail" , 36)
    ,GetConversation("GetConversation" , 37)
    ,GetAnswerResult("GetAnswerResult" , 38)
    ,DeleteMessage("DeleteMessage" , 39)
    ,GetterWiner("GetterWinner" , 40)
    ,UnSeen("unseen" , 41)
    ;
    

    private String methodName;
    private int methodValue;

    public int getMethodValue() {
        return this.methodValue;
    }

    private MyMethods(String toString, int value) {
        this.methodName = toString;
        this.methodValue = value;
    }

    public String toString() {
        return this.methodName;
    }
}
