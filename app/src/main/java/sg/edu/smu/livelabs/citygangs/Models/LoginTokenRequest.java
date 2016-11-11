package sg.edu.smu.livelabs.citygangs.Models;

/**
 * Created by tomrolandus on 11/11/16.
 */

public class LoginTokenRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
