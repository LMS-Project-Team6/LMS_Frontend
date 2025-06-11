package model;

import vo.Lib;

public class LibModel {
    private static LibModel instance;
    private Lib loggedInLib;

    private LibModel() {
        loggedInLib = new Lib();
    }

    public static LibModel getInstance() {
        if (instance == null) {
            instance = new LibModel();
        }
        return instance;
    }

    public void setLoggedInLib(Lib lib) {
        this.loggedInLib = lib;
    }

    public Lib getLoggedInLib() {
        return loggedInLib;
    }

    public void clear() {
        loggedInLib = new Lib();
    }
}
