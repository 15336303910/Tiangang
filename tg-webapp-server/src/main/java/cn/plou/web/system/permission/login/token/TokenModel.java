package cn.plou.web.system.permission.login.token;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 */
public class TokenModel {

    //用户id
    private String userId;

    //随机生成的uuid
    private String token;

    private String userName;
    
    public TokenModel(String userId, String token, String userName) {
        this.userId = userId;
        this.token = token;
        this.userName = userName;
    }
	  public TokenModel(String userId, String token) {
	      this.userId = userId;
	      this.token = token;
	  }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUserName() {
      return userId;
   }

	  public void setUserName(String userName) {
	      this.userName = userName;
	  }
}