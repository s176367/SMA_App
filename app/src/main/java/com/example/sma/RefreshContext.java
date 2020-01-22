package com.example.sma;

// @Author Gutav Kristensen s180077
// Bruges til at fortælle klasser hvorvidt de skal opdateres eller ej.
public class RefreshContext {
    static Boolean home = true;
    static Boolean contacts = true;
    static Boolean invites = true;

    public static void setInvites(Boolean invites) {
        RefreshContext.invites = invites;
    }

    public static Boolean getInvites() {
        return invites;
    }

    public static Boolean getContacts() {
        return contacts;
    }

    public static Boolean getHome() {
        return home;
    }

    public static void setContacts(Boolean contacts) {
        RefreshContext.contacts = contacts;
    }

    public static void setHome(Boolean home) {
        RefreshContext.home = home;
    }
}
