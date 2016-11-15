package sg.edu.smu.livelabs.citygangs.Models;

/**
 * Created by tomrolandus on 16/11/16.
 */

public class AddAreaRequest {
    private double latitude;
    private double longitude;
    private int team_id;
    private double length;

    public double getLatitute() {
        return latitude;
    }

    public void setLatitude(double latitute) {
        this.latitude = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
