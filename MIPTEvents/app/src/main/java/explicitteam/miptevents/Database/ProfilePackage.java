package explicitteam.miptevents.Database;

/**
 * Created by Anatoly on 20.11.2016.
 */

public class ProfilePackage {

    private boolean registered;

    private String nicename;

    private String email;

    public ProfilePackage(String nicename, String email) {
        this.nicename = nicename;
        this.email = email;
        registered = true;
    }

    public ProfilePackage(boolean registered) {
        this.registered = registered;
    }

    public String getNicename() {
        return nicename;
    }

    public String getEmail() {
        return email;
    }

    public boolean isRegistered() {
        return registered;
    }
}
