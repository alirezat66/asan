package ir.greencode.advicelawAndroid.choselawyer;

import ir.greencode.advicelawAndroid.retrofit.respObject.WaitedAdviceDetial;

public interface ChoseLawyerInterface {
   public void onReadyLawyer(WaitedAdviceDetial detial);

   void onError(String str);

    void onSuccessCancel();
}
