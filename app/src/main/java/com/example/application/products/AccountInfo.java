package com.example.application.products;

<<<<<<< HEAD
class AccountInfo {
=======
/**
 * Created by Uta on 2016/08/07.
 */

public class AccountInfo {
>>>>>>> origin/master
    private static AccountInfo instance = new AccountInfo();
    private int prefectureId;
    private String mailAddress;

<<<<<<< HEAD
    private AccountInfo() {}

    static AccountInfo getInstance() {
        return instance;
    }

    void setPrefectureId(int prefectureId) {
        this.prefectureId = prefectureId;
    }

    void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    int getPrefectureId() { return prefectureId; }

    String getMailAddress() {
=======
    AccountInfo() {}

    public static AccountInfo getInstance() {
        return instance;
    }

    public void setPrefectureId(int prefectureId) {
        this.prefectureId = prefectureId;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public int getPrefectureId() {

        return prefectureId;
    }

    public String getMailAddress() {
>>>>>>> origin/master
        return mailAddress;
    }
}
