package com.example.application.products;

class AccountInfo {
    private static AccountInfo instance = new AccountInfo();
    private int prefectureId;
    private String mailAddress;

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
        return mailAddress;
    }
}
