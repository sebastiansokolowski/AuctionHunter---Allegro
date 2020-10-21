package com.sebastian.sokolowski.auctionhunter.database.models;

import io.realm.RealmObject;

/**
 * Created by Sebastian Sokołowski on 08.03.17.
 */

public class RealmString extends RealmObject {
    private String string;

    public RealmString() {
    }

    public RealmString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealmString string1 = (RealmString) o;

        return string != null ? string.equals(string1.string) : string1.string == null;

    }

    @Override
    public int hashCode() {
        return string != null ? string.hashCode() : 0;
    }
}
