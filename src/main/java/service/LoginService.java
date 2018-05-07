package service;

public class LoginService {
    private String urlAPI;

    public LoginService(String urlAPI) {
        this.urlAPI = urlAPI;
    }

    public boolean login(String username,String password){
        return true;
    }

    public String getUrlAPI() {
        return urlAPI;
    }
}
