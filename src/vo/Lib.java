package vo;

public class Lib {
    private String libId;
    private String libPw;
    private String libName;
    private String libEmail;
    private String libBirth;
    private String libPNum;
    private int adminNY;
    private int applyNY;
    private String createdDate;
    private String modifiedDate;

    // 기본 생성자
    public Lib() {}

    // Getter & Setter
    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public String getLibPw() {
        return libPw;
    }

    public void setLibPw(String libPw) {
        this.libPw = libPw;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getLibEmail() {
        return libEmail;
    }

    public void setLibEmail(String libEmail) {
        this.libEmail = libEmail;
    }

    public String getLibBirth() {
        return libBirth;
    }

    public void setLibBirth(String libBirth) {
        this.libBirth = libBirth;
    }

    public String getLibPNum() {
        return libPNum;
    }

    public void setLibPNum(String libPNum) {
        this.libPNum = libPNum;
    }

    public int getAdminNY() {
        return adminNY;
    }

    public void setAdminNY(int adminNY) {
        this.adminNY = adminNY;
    }

    public int getApplyNY() {
        return applyNY;
    }

    public void setApplyNY(int applyNY) {
        this.applyNY = applyNY;
    }

    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
