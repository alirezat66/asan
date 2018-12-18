package ir.greencode.advicelawAndroid.retrofit;


import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AcceptReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AuthReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CallUpdateReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CatReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.DoneAdviceDetailReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.GetWaitedDetailsReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.InsertReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileInfoReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileUpdateReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.SubCatReq;
import ir.greencode.advicelawAndroid.utils.Utility;

public class CallerService {

    public static void signUp( ServerListener sl, AuthReq req) {

        new ServerResponse().setCall(MyMethods.Authentication, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).Authenticate(req.getMobile()), sl);
    }
    public static void getCats(ServerListener sl , CatReq req){
        new ServerResponse().setCall(MyMethods.GetCats, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).GetCats(req.getToken(),req.getTimestamp(),req.getAdvicerId()), sl);

    }
    public static void getSubCats(ServerListener sl , SubCatReq req){
        new ServerResponse().setCall(MyMethods.GetSubCat, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).GetSubCats(req.getToken(),req.getTimestamp(),req.getId()), sl);

    }

    public static void getAdviceList(ServerListener sl,String token) {
        new ServerResponse().setCall(MyMethods.GetActiveList, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).GetActiveAdvice(token), sl);

    }

    public static void getAdviceDetail(ServerListener sl, GetWaitedDetailsReq req) {
        new ServerResponse().setCall(MyMethods.GetWaitedDetail, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).GetWaitedRequestDetial(req.getToken(),req.getRequestId()), sl);

    }

    public static void insertRequest(ServerListener sl, InsertReq req) {
        if(req.getDocuments().size()==0){
            new ServerResponse().setCall(MyMethods.InsertRequest, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).createRequest(req.getToken(),req.getUserId(),req.getCatId(),"[]",req.getDesc()), sl);

        }else {
            String docs = "";
            docs ="[";
            for(String doc : req.getDocuments() ){
                docs=docs+doc+",";
            }
            docs = docs.substring(0,docs.length()-1);
            docs = docs+"]";
            new ServerResponse().setCall(MyMethods.InsertRequest, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).createRequest(req.getToken(),req.getUserId(),req.getCatId(),docs,req.getDesc()), sl);

        }

    }

    public static void getActiveAdviceDetail(ServerListener sl, GetWaitedDetailsReq req) {
        new ServerResponse().setCall(MyMethods.GetActivatedDetail, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).GetActivatedReqRequestDetial(req.getToken(),req.getRequestId()), sl);

    }

    public static void acceptRequest(ServerListener sl, AcceptReq req) {
        new ServerResponse().setCall(MyMethods.AcceptRequest, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).acceptAdviser(req.getToken(),req.getId(),req.getAdviserId(),req.getResponseHourId(),req.getCallTimestamp(),req.getHourCount()), sl);

    }

    public static void cancelReq(ServerListener sl, CancelReq req) {
        new ServerResponse().setCall(MyMethods.CancelReq, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).cancelRequest(req.getToken(),req.getId(),req.getUserId()), sl);

    }
    public static void cancelOffer(ServerListener sl, CancelReq req) {
        new ServerResponse().setCall(MyMethods.CancelOffer, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).cancelOffer(req.getToken(),req.getId(),req.getUserId()), sl);

    }
    public static void getProfileInfo(ServerListener sl, ProfileInfoReq req) {
        new ServerResponse().setCall(MyMethods.ProfileInfo, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).getProfileInfo(req.getToken(), req.getId()), sl);
    }
        public static void updateProfile(ServerListener sl, ProfileUpdateReq req) {
        new ServerResponse().setCall(MyMethods.UpdateProfile, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).updateProfile(req.getToken(),req.getId(),req.getFirstName(),
                req.getLastName(),req.getBirthDate(),req.getSex(),req.getEmail(),req.getUserImg(),req.getNationalCode()), sl);

    }

    public static void getDoneDetial(ServerListener sl, DoneAdviceDetailReq req) {
        new ServerResponse().setCall(MyMethods.GetDoneDetial,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).getDoneRequestDetial(req.getToken(),req.getOfferId()), sl);

    }
    public static void getMyRequests(ServerListener sl, String token) {
        new ServerResponse().setCall(MyMethods.GetMyRequest,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).getMyRequest(token), sl);

    }
    public static void getCallState(ServerListener sl, String token,String offerId) {
        new ServerResponse().setCall(MyMethods.GetCallState,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).checkCallState(token,offerId), sl);

    }

    public static void updateCall(ServerListener sl, CallUpdateReq req) {
        new ServerResponse().setCall(MyMethods.UPDATECALL,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).updateCall(req.getToken(),req.getUserId(),req.getCallId(),req.getStatus(),
                        req.getTime(),req.getDuration(),req.getDesc()), sl);


    }
    public static void sendGCMToken(ServerListener sl, String token,String gcmToken) {
        new ServerResponse().setCall(MyMethods.SendGCMToken,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).sendGcmToken(token,gcmToken), sl);


    }
    public static void logOut(ServerListener sl, String token) {
        new ServerResponse().setCall(MyMethods.Logout,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).logOut(token), sl);


    }
    // 1- ok
   /* public static void signUp( ServerListener sl, SignUpRequest req) {

        new ServerResponse().setCall(MyMethods.SignUp, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).signUp(req.getUsername(), req.getReferrer()), sl);
    }

    public static void setPass(RegisterRequest req,ServerListener sl) {
        new ServerResponse().setCall(MyMethods.SetPass,((reqInterface) ApiClient.getClient().create(reqInterface.class)).setPass(req.getUserName(), req.getPass()), sl);
    }

    public static void reSend(SignUpRequest req,ServerListener sl) {
        new ServerResponse().setCall(MyMethods.ResendSMS, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).signUp(req.getUsername(), req.getReferrer()), sl);

    }
    public static void login(LoginRequest req, ServerListener sl) {
        new ServerResponse().setCall(MyMethods.Login, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).login(req.getUsername(), req.getPassword()), sl);

    }

    public static void forgetPassStepOne(ChangePassStepOneReq req,ServerListener sl) {
        new ServerResponse().setCall(MyMethods.ForgetPassStepOne, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).forgetPassStepOne(req.getUserName()), sl);

    }

    public static void forgetPassStepTwo(ChangePassStepTwoReq req,ServerListener sl) {
        new ServerResponse().setCall(MyMethods.ForgetPassStepTwo, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).forgetPassStepTwo(req.getUserName(),req.getCode()), sl);

    }

    public static void forgetPassStepThree(ChangePassReq req, ServerListener sl) {
        new ServerResponse().setCall(MyMethods.ForgetPassStepThree, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).forgetPassStepThree(req.getPass(),req.getUser_id()), sl);

    }
    public static void setProfile(Profile req , ServerListener sl){
        new ServerResponse().setCall(MyMethods.SetProfile, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).setProfile(req.getPhone(),req.getAge(),req.getWeight(),req.getHeight(),req.getGender()+"",req.getBlood()+"",req.getfName(),req.getlName(),req.getBirthDay(),req.getSickness(),req.getAlergy(),req.getImg()), sl);

    }
    public static void addDrug(PillObject req , String userId , ServerListener sl){
        new ServerResponse().setCall(MyMethods.AddDrug,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).addDrug
                        (req.getid()+"",req.getMidname(),req.getCouseOfUse(),req.getDrName(),req.getCatName(),req.getCatColor()+"",
                                req.getCatring()+"",req.getTypeOfUsage()+"",(req.isRegular())+"",
                                req.getRepeatUsageDay()+"",req.getDays(),req.getDiffrenceOfUsage()+"",req.getCountOfUsagePerDay()+""
                        ,req.getStardHour()+"",req.getStartMin()+"",req.getUnitUse(),req.getUnitsCount(),req.getUnitTimes(),req.getUseType()+"",
                        req.getAllUseDays()+"",req.getTotalAmounts()+"",req.getFirstAlarmTime()+"",req.getDaysOfUse()+"",req.getAmountOfUse()+"",
                                req.getDescription(),req.getLight()+"",req.getVibrate()+"",req.getReminderDays()+"",
                                req.getAllPillCount()+"",userId), sl);

    }
    public static void addUsage(PillUsage req , String userId,ServerListener sl){
        new ServerResponse().setCall(MyMethods.AddUsage,
                ((reqInterface) ApiClient.getClient().create(reqInterface.class)).addDrugUsage(req.getPillId()+"",
                        req.getid()+"",req.getTime()+"",req.getUseTime()+"",req.getDescription(),req.getUseTime()+""
                ,(req.isHasDelay()+""),req.getCatNme(),req.getCatColor()+"",req.getCatRingtone(),req.getDrName(),req.getUnit(),
                        req.getUnitAmount(),req.getCountPerDay(),req.getUsedTime()+"",req.getSnoozCount()+"",req.getSetedTime()+"",
                        userId),sl);
    }

    public static void getDrug(GetDragReq req , ServerListener sl){
        new ServerResponse().setCall(MyMethods.GetDrugs, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).getDrug(req.getUser_id()+""), sl);

    }*/
    /*public static void getAlarts(String token, ServerListener sl, GetAlerts req) {

        new ServerResponse().setCall(myMethods.GetAlaets, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getAlerts(req.getLoaded()), sl);
    }
    public static void getAlartsDetail(String token, ServerListener sl, alertDetail req) {

        new ServerResponse().setCall(myMethods.GetAlaetsDetail, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).alertDetail(req.getAlert_id()), sl);
    }

    // 2- ok
    public static void register(String token , ServerListener sl , RegisterReq req){

        new ServerResponse().setCall(myMethods.Register, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).register(req.getName() , req.getFamily() , req.getMob() , req.getPassword() , req.getConfirm_password()) , sl);
    }

    // 3- ok
    public static void activeCode(String token , ServerListener sl , ActiveCodeReq req){

        new ServerResponse().setCall(myMethods.ActiveCode , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).activeCode(req.getCode() , req.getMob()) , sl);
    }
    public static void getConversation(String token , ServerListener sl , ConversationReq req){

        new ServerResponse().setCall(myMethods.GetConversation , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getConversation(req.getLoaded() , req.getUser_id()) , sl);
    }
    // 4- ok
    public static void resendSMS(String token , ServerListener sl , ResendSMSReq req){

        new ServerResponse().setCall(myMethods.ResendSMS , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).resendSMS(req.getMob()) , sl);
    }

    // 5- ok
    public static void getLoanList(Context token , ServerListener sl , GetLoanListReq req){

        // in getLoanList API nothing to POST
        new ServerResponse().setCall(myMethods.GetLoanList , ((reqInterface) ApiClient.getClient2(token).create(reqInterface.class)).getLoanList() , sl);
    }

    // 6- ok
    public static void createLoan(String token , ServerListener sl , CreateLoanReq req){

        new ServerResponse().setCall(myMethods.CreateLoan , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).
                createLoan(req.getTitle() , req.getAmount() , req.getPayment_count(), req.getPayment_amount(), req.getStart_date() , req.getLottery_date() ,req.getMember_count(),req.getIs_public(),req.getSms_reminder(), req.getReminder_time() , req.getExplain()) ,  sl);
    }

    // 7- ok
    public static void getLoanDetail(String token , ServerListener sl , GetLoanDetailReq req){

        new ServerResponse().setCall(myMethods.GetLoanDetail , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getLoanDetails(req.getLoan_id()) ,  sl);
    }
    public static void deleteMessage(String token , ServerListener sl , DeleteMessageReq req){

        new ServerResponse().setCall(myMethods.DeleteMessage , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).DeleteMessage(req.getMessage_id()) ,  sl);
    }
    // 8- ok
    public static void getLoanUser(String token , ServerListener sl , GetLoanUserReq req){

        new ServerResponse().setCall(myMethods.GetLoanUser , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getLoanUser(req.getLoan_id()) ,  sl);
    }

    // 9- ok
    public static void addUserToLoan(String token , ServerListener sl , AddUserToLoanReq req){

        new ServerResponse().setCall(myMethods.AddUserToLoan , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).addUserToLoan(req.getLoan_id() , req.getMob()) ,  sl);
    }

    // 10- ok
    public static void archiveUser(String token , ServerListener sl , ArchiveUserReq req){

        new ServerResponse().setCall(myMethods.ArchiveUser , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).archiveUser(req.getLoanId() , req.getUserId()) , sl);

    }

    // 11- ok
    public static void deleteUser(String token, ServerListener sl , DeleteUserReq req){
        new ServerResponse().setCall(myMethods.DeleteUser, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).deleteUser(req.getLoanId(), req.getUserId()), sl);
    }

    // 12 - ok
    public static void updateProfile(String token, ServerListener sl , UpdateProfileReq req){
        new ServerResponse().setCall(myMethods.UpdateProfile, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).updateProfile(req.getName(),req.getNational_code(),req.getSheba(), req.getMail()) , sl);
    }

    // 13-
    public static void updateProfilePicture(String token, ServerListener sl , UpdateProfilePictureReq req){
        new ServerResponse().setCall(myMethods.UpdateProfilePicture, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).updateProfilePicture(req.getFile()) , sl);
    }

    // 14-
    public static void confirmUser(String token, ServerListener sl , ConfirmUserReq req){
        new ServerResponse().setCall(myMethods.ConfirmUser, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).confirmUser(req.getLoan_id(), req.getUser_id(),req.getSms_code()), sl);
    }

    // 15- wait - > nima
    public static void payByAccess(String token, ServerListener sl , PayByAccessReq req){
        new ServerResponse().setCall(myMethods.PayByAccess, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).payByAccess(req.getLoan_id(),req.getUser_id(), req.getPay_by_id()), sl);
    }
    public static void AlertResult(String token, ServerListener sl , GetResultAlart req){
        new ServerResponse().setCall(myMethods.GetAnswerResult, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).AlertResult(req.getAlart_id(),req.getAnswer()), sl);
    }
    // 16 -
    public static void getPayByAccessList(String token, ServerListener sl , GetPayByAccessListReq req){
        new ServerResponse().setCall(myMethods.GetPayByAccessList, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getPayByAccessList(req.getLoan_id()) , sl);
    }

    // 17 -
    public static void prepareInvoice(String token, ServerListener sl , PrepareInvoiceReq req){
        new ServerResponse().setCall(myMethods.PrepareInvoice, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).prepareInvoice(req.getLoan_id()) , sl);
    }
    public static void setTransactionResult(String token, ServerListener sl , setOfflineTransactionReq req){
        new ServerResponse().setCall(myMethods.setTransactionResult, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).setOfflineTransactionResult(req.getTransaction_id(),req.getTransaction_result()) , sl);
    }
    // 18 -
    public static void getPaymentDetail(String token, ServerListener sl , GetPaymentDetailReq req){
        new ServerResponse().setCall(myMethods.GetPaymentDetail, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getPaymentDetail(req.getLoan_id(),req.getPay_for(),req.getPeriod_id()) , sl);
    }
    public static void getUnseen(String token, ServerListener sl ){
        new ServerResponse().setCall(myMethods.UnSeen, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).GetUnseen(),sl);
    }

    public static void getPaymentReport(String token, ServerListener sl , GetPaymentReport req){
        new ServerResponse().setCall(myMethods.GetPaymentReport, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getPaymentReport(req.getLoan_id()) , sl);
    }
    public static void createLottary(String token, ServerListener sl , CreateLottery req){
        new ServerResponse().setCall(myMethods.CreateLottery, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).createLottary(req.getType(),req.getPeriod_id(),req.getLottery_time(),req.getWinnerId()) , sl);
    }
    public static void prepareLottary(String token, ServerListener sl , PrepareLottery req){
        new ServerResponse().setCall(myMethods.PrepareLottery, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).prepareLottary(req.getLoanID()) , sl);
    }
    public static void getLottaryGeneral(String token, ServerListener sl , GetLotteryGeneralReq req){
        new ServerResponse().setCall(myMethods.GetLotteryGeneral, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getLottaryGeneral(req.getLoanID()) , sl);
    }

    // 19 -
    public static void userInfo(Context token, ServerListener sl , UserInfoReq req){
        reqInterface call = (reqInterface) ApiClient2.getClient(token).create(reqInterface.class);
        new ServerResponse().setCall(myMethods.UserInfo, (call).userInfo(req.getUsername()) , sl);
    }

    // 20 -
    public static void userInfo(String token, ServerListener sl , UserInfoReq req){
        reqInterface call = (reqInterface) ApiClient.getClient(token).create(reqInterface.class);
        new ServerResponse().setCall(myMethods.UserInfo, (call).userInfo(req.getUsername()) , sl);
    }

    // 21 -
    public static void changePassword(String token, ServerListener sl , ChangePasswordReq req){
        new ServerResponse().setCall(myMethods.ChangePassword, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).changePassword(req.getPassword(),req.getConfirm_password(),req.getOld_password()) , sl);
    }

    // 22 -
    public static void UploadFile(ServerListener context,  MultipartBody.Part photo,String token) {

        ServerResponse serverResponse = new ServerResponse();
        myMethods mytrack_com_track_driver_enums_myMethods = myMethods.Authentication;
        serverResponse.setCall(myMethods.Upload, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).UploadImage(photo), context);
    }

    // 23 -
    public static void getUserStatistics(String token , ServerListener sl , GetUserStatisticsReq req){

        // in getUserStatistics API nothing to POST in Body just send in header token
        new ServerResponse().setCall(myMethods.GetUserStatistic , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getUserStatistics(null) , sl);
    }

    // 24 -
    public static void createInvoiceOffline(String token , ServerListener sl , CreateInvoiceOfflineReq req , MultipartBody.Part photo){

        new ServerResponse().setCall(myMethods.CreateInvoiceOffline , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).offlinewithPic(photo , req.getType() , req.getPeriod_id() , req.getReceiptCode() , req.getTrackingCode() , req.getTime() , req.getDescription()) , sl);
    }

    // 25 -
    public static void sendMessage(String token, ServerListener sl , SendMessageReq req){
        new ServerResponse().setCall(myMethods.SendMessage, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).sendMessage(req.getLoan_id() , req.getReceiver_user_id(),req.getMessage()) , sl);
    }

    // 26 -
    public static void getMessage(String token, ServerListener sl , GetMessageReq req){
        new ServerResponse().setCall(myMethods.GetMessage, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getMessage(req.getLoaded()) , sl);
    }

    // 27 -
    public static void getUserConversation(String token, ServerListener sl , GetUserConversationReq req){
        new ServerResponse().setCall(myMethods.GetUserConversation, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getUserConversation(req.getLoaded() , req.getUser_id()) , sl);
    }

    public static void resetPassword(String token , ServerListener sl , ResetPasswordReq req){

        new ServerResponse().setCall(myMethods.ResetPassword , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).resetPassword(req.getMob()) , sl);
    }

    public static void prepareWithdrawal(String token, ServerListener sl , PrepareWithdrawalReq req){
        new ServerResponse().setCall(myMethods.PrepareWithdrawal, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).prepareWithdrawal(req.getLotteryID()) , sl);
    }

    public static void createWithdrawal(String token, ServerListener sl , CreateWithdrawalReq req){
        new ServerResponse().setCall(myMethods.CreateWithdrawal, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).createWithdrawal(req.getLotteryID() , req.getAmount()) , sl);
    }

    public static void getWithdrawalDetails(String token, ServerListener sl , GetWithdrawalDetailsReq req){
        new ServerResponse().setCall(myMethods.GetWithdrawalDetails, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).getWithdrawalDetails(req.getLotteryID()) , sl);
    }

    public static void UploadFileWithData(String token , ServerListener context, CreateInvoiceOfflineReq req, MultipartBody.Part photo) {
        RequestBody type = RequestBody.create(MultipartBody.FORM, req.getType());
        RequestBody periodID = RequestBody.create(MultipartBody.FORM, req.getPeriod_id());
        RequestBody receiptCode = RequestBody.create(MultipartBody.FORM, req.getReceiptCode());
        RequestBody trackingCode = RequestBody.create(MultipartBody.FORM, req.getTrackingCode());
        RequestBody time = RequestBody.create(MultipartBody.FORM, req.getTime());
        RequestBody description = RequestBody.create(MultipartBody.FORM, req.getDescription());
        RequestBody payFor = RequestBody.create(MultipartBody.FORM, req.getPay_for());
        ServerResponse serverResponse = new ServerResponse();
        //myMethods mytrack_com_track_driver_enums_myMethods = myMethods.Authentication;
        serverResponse.setCall(myMethods.CreateInvoiceOffline, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).createInvoiceOfflineWithFile(type, periodID, receiptCode, trackingCode, time,description,payFor, photo), context);
    }
    public static void UploadFileWithoutData(String token , ServerListener context, CreateInvoiceOfflineReq req) {

        ServerResponse serverResponse = new ServerResponse();
        //myMethods mytrack_com_track_driver_enums_myMethods = myMethods.Authentication;
        serverResponse.setCall(myMethods.CreateInvoiceOffline, ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).createInvoiceOfflineWithFile(req.getType(), req.getPeriod_id(), req.getReceiptCode(), req.getTrackingCode(), req.getTime(),req.getDescription(),req.getPay_for()), context);
    }
    // 24 -
    public static void resetPassword(String token , ServerListener sl , PasswordResetReq req){

        // in getUserStatistics API nothing to POST in Body just send in header token
        new ServerResponse().setCall(myMethods.ResetPassword , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).resetPassword(req.getMob()) , sl);
    }


    // 25 -
    public static void resetPasswordActiveCode(String token , ServerListener sl , ResetPasswordActiveCodeReq req){

        // in getUserStatistics API nothing to POST in Body just send in header token
        new ServerResponse().setCall(myMethods.ResetPasswordActiveCode , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).resetPasswordActiveCode(req.getMob(),req.getCode(),req.getOne_time_code()) , sl);
    }

    // 25 -
    public static void resetPasswordSetNewPassword(String token , ServerListener sl , ResetPasswordSetNewPassword req){

        // in getUserStatistics API nothing to POST in Body just send in header token
        new ServerResponse().setCall(myMethods.ResetPasswordSetNewPassword , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).resetPasswordSetNewPassword(req.getMob(),req.getCode(),req.getOne_time_code(),req.getPassword(),req.getConfirmPassword()) , sl);
    }
    public static void GetWinner(String token , ServerListener sl , String lottery_id){

        // in getUserStatistics API nothing to POST in Body just send in header token
        new ServerResponse().setCall(myMethods.GetterWiner , ((reqInterface) ApiClient.getClient(token).create(reqInterface.class)).GetWinner(lottery_id) , sl);
    }*/
  /*  public static void sendPoint(ServerListener context, PointReq req) {
        new ServerResponse().setCall(myMethods.SavePoi, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).sendPoint(req.getUserid(), req.getAction(), req.getKey(), req.getData(), req.getTripid()), context);
    }

    public static void sendStudent(ServerListener context, StudentReq req) {
        new ServerResponse().setCall(myMethods.Student, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).getStudent(req.getUserid(), req.getAction(), req.getKey(), req.getStep()), context);
    }

    public static void sendInp(ServerListener context, TripReq req) {
        new ServerResponse().setCall(myMethods.Trip, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).setTrip(req.getUserid(), req.getAction(), req.getKey(), req.getStep(), req.getStudentid(), req.getTripid(), req.getType(), req.getLat(), req.getLon()), context);
    }

    public static void StartTrip(ServerListener context, StartTripReq req) {
        new ServerResponse().setCall(myMethods.TripStart, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).StartTrip(req.getUserid(), req.getAction(), req.getKey(), req.getStep(), req.getType(), req.getLat(), req.getLang(), req.getSpeed()), context);
    }

    public static void EndTrip(ServerListener context, EndTripReq req) {
        new ServerResponse().setCall(myMethods.TripENd, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).EndTrip(req.getUserid(), req.getAction(), req.getKey(), req.getStep(), req.getType(), req.getTripid(), req.getLat(), req.getLang(), req.getSpeed()), context);
    }

    public static void DriverReq(ServerListener context, DriverInfoReq req) {
        new ServerResponse().setCall(myMethods.Driver, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).DriverProfile(req.getUserid(), req.getAction(), req.getKey()), context);
    }

    public static void About(ServerListener context, AboutReq req) {
        new ServerResponse().setCall(myMethods.About, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).About(req.getAction(), req.getKey(), req.getType()), context);
    }

    public static void News(ServerListener context, NewsReq req) {
        new ServerResponse().setCall(myMethods.News, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).News(req.getAction(), req.getKey(), req.getType(), req.getStep(), req.getUserid()), context);
    }

    public static void Messages(ServerListener context, MessageReq req) {
        new ServerResponse().setCall(myMethods.MessageList, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).Messages(req.getAction(), req.getKey(), req.getType(), req.getStep(), req.getUserid()), context);
    }

    public static void ChangePass(ServerListener context, ChangePassReq req) {
        new ServerResponse().setCall(myMethods.ChangePass, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).ChangePass(req.getAction(), req.getKey(), req.getType(), req.getStep(), req.getUserid(), req.getPassword()), context);
    }

    public static void SendMessage(ServerListener context, SendMessageReq req) {
        new ServerResponse().setCall(myMethods.SendMessage, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).SendMessage(req.getAction(), req.getKey(), req.getMessage(), req.getTitle(), req.getStep(), req.getUserid()), context);
    }

    public static void DeleteMessage(ServerListener context, DeleteMessageReq req) {
        new ServerResponse().setCall(myMethods.DeleteMessages, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).DeleteMessage(req.getAction(), req.getKey(), req.getStep(), req.getUserid(), req.getId()), context);
    }

    public static void SaveOrder(ServerListener context, OrderReq req) {
        new ServerResponse().setCall(myMethods.SaveOrder, ((reqInterface) ApiClient.getClient().create(reqInterface.class)).SaveOrder(req.getAction(), req.getKey(), req.getStep(), req.getData().toString(), req.getUserid()), context);
    }

    */



}
