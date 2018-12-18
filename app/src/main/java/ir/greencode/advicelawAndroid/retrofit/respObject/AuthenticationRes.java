package ir.greencode.advicelawAndroid.retrofit.respObject;

public class AuthenticationRes extends ErrorResult {

    AuthRes result;

    public AuthRes getResult() {
        return result;
    }

   public class AuthRes {
        String token;
        String code;
        String userName;
        String userFamily;
        String userImage;
        int userId;

       public int getId() {
           return userId;
       }

       public String getToken() {
            return token;
        }

        public String getCode() {
            return code;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserFamily() {
            return userFamily;
        }

        public String getUserImage() {
            return userImage;
        }
    }


}
