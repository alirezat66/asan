package ir.greencode.advicelawAndroid.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ir.greencode.advicelawAndroid.utils.ToJsonClass;

@Entity(tableName = "profile")
public class Profile extends ToJsonClass{
   String firstName;
   String lastName;
   @PrimaryKey(autoGenerate = false)
   int userId;
   String mobile;
   String email;
   String sex;
   long birthDate;
   String nationalCode;
   String userImg;
   int timeOfCheck;


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public void setTimeOfCheck(int timeOfCheck) {
        this.timeOfCheck = timeOfCheck;
    }

    public String getFName() {
        return firstName;
    }

    public String getLName() {
        return lastName;
    }

    public int getId() {
        return userId;
    }

    public String getMob() {
        return mobile;
    }

    public String getMail() {
        return email;
    }

    public String getGender() {
        return sex;
    }

    public long getBDate() {
        return birthDate*1000;
    }

    public String getNCode() {
        return nationalCode;
    }

    public String getUserImage() {
        return userImg;
    }

    public int getTimeCheck() {
        return timeOfCheck;
    }
}
