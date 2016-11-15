package sg.edu.smu.livelabs.citygangs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tomrolandus on 11/11/16.
 */

public class User {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("photo_1")
    @Expose
    private String photo_1;

    @SerializedName("photo_2")
    @Expose
    private String photo_2;

    @SerializedName("current_distance")
    @Expose
    private int currentDistance;

    @SerializedName("current_area")
    @Expose
    private int current_area;

    @SerializedName("total_distance")
    @Expose
    private int totalDistance;



    @SerializedName("kills")
    @Expose

    private int kills;


    @SerializedName("team_id")
    @Expose

    private int teamID;

    @SerializedName("face_id")
    @Expose
    private String faceId;

    @SerializedName("group_id")
    @Expose
    private String groupId;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @SerializedName("latitude")
    @Expose
    private double latitude;


    public User(int id, String username, String name, String email, String password, int kills, int currentDistance, int totalDistance, double longitude, double latitude, int teamID){
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.kills = kills;
        this.currentDistance = currentDistance;
        this.totalDistance = totalDistance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.teamID = teamID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(int currentDistance) {
        this.currentDistance = currentDistance;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String face_id) {
        this.faceId = face_id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setCurrent_area(int current_area) {
        this.current_area = current_area;
    }
    public int getCurrent_area() {
        return current_area;
    }

}