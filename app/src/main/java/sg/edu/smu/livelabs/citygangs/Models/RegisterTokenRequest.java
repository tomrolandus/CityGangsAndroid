package sg.edu.smu.livelabs.citygangs.Models;

/**
 * Created by tomrolandus on 14/11/16.
 */

public class RegisterTokenRequest {
    private String email;
    private String password;
    private String username;
    private int team_id;
    private String group_id;
    private String face_id;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int teamId) {
        this.team_id = teamId;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
