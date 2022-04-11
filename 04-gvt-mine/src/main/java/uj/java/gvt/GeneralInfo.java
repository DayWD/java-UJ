package uj.java.gvt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GeneralInfo implements Serializable {
    public Long latestVersion;
    public List<String> trackedFiles = new ArrayList<>();

    public GeneralInfo() {
    }

    public GeneralInfo(Long version) {
        latestVersion = version;
    }

    public Long upgradeVersion() {
        latestVersion += 1;
        return latestVersion;
    }
}
