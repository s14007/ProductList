package com.example.application.products;

/**
 * Created by Uta on 2016/08/07.
 */

public class AccountInfo {
    private static AccountInfo instance = new AccountInfo();
    private int prefectureId;
    private String mailAddress;

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
        return mailAddress;
    }
}
