package sg.edu.smu.livelabs.citygangs.Models;

/**
 * Created by tomrolandus on 12/11/16.
 */

public class ServerAccessRequest {
    private String accept;
    private String authorization;

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
