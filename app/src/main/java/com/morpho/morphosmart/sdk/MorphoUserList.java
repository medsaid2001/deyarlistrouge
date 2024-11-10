package com.morpho.morphosmart.sdk;

import java.util.ArrayList;

public class MorphoUserList {
    private ArrayList<MorphoUser> morphoUsers = new ArrayList<>();

    public MorphoUser getUser(int i) {
        if (i < this.morphoUsers.size()) {
            return this.morphoUsers.get(i);
        }
        return null;
    }

    public int getNbUser() {
        return this.morphoUsers.size();
    }

    private void addUser(MorphoUser morphoUser) {
        if (morphoUser != null) {
            this.morphoUsers.add(morphoUser);
        }
    }
}
