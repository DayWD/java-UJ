package uj.java.gvt;

import java.io.Serializable;

public class GvtVersion implements Serializable {
    public Long version;
    public String commitMessage;

    public GvtVersion() {
    }

    public GvtVersion(Long version, String message) {
        this.version = version;
        this.commitMessage = message;
    }
}
