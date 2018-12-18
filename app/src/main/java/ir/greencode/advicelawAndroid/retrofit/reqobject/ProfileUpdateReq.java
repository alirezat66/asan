package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class ProfileUpdateReq {
    String token;
    int id;
    String firstName;
    String lastName;
    String nationalCode;
    long birthDate;
    String sex;
    String email;
    String userImg;

    public ProfileUpdateReq(String token, int id, String firstName, String lastName, String nationalCode, long birthDate, String sex, String email, String userImg) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.userImg = userImg;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public long getBirthDate() {
        return birthDate/1000;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getUserImg() {
        return userImg;
    }
}
