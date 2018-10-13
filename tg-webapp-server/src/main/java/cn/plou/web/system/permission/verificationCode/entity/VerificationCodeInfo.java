package cn.plou.web.system.permission.verificationCode.entity;

public class VerificationCodeInfo {
    private String uuid;
    private String image;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "VerificationCodeInfo{" +
                "uuid='" + uuid + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
