package com.adc.mycircle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kyle on 6/25/2017.
 */

public class UserData {

    //TODO: private static String userID;
    private static String username;
    private static String photoURL;
    private static String firstName;
    private static String lastName;
    private static String email;

    public static void SetUserData (String user_name, String photo_URL, String first_Name, String last_Name, String e_mail) {
        username = user_name;
        photoURL = photo_URL;
        firstName = first_Name;
        lastName = last_Name;
        email = e_mail;
    }

    public static String GetUsername () {
        return username;
    }

    public static String GetPhotoURL () {
        return photoURL;
    }

    public static void SetPhotoURL (String photo_URL) {
        photoURL = photo_URL;
    }

    public static String GetFirstName () {
        return firstName;
    }

    public static void SetFirstName (String first_Name) {
        firstName = first_Name;
    }

    public static String GetLastName () {
        return lastName;
    }

    public static void SetLastName (String last_Name) {
        lastName = last_Name;
    }

    public static String GetEmail () {
        return email;
    }

    public static void SetEmail (String e_mail) {
        email = e_mail;
    }

    public static String buildUserObject () {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ ");
        stringBuilder.append("username:");
        stringBuilder.append(username);
        stringBuilder.append(", ");
        stringBuilder.append("photoUrl:");
        stringBuilder.append(" ");
        stringBuilder.append(", ");
        stringBuilder.append("firstname:");
        stringBuilder.append(firstName);
        stringBuilder.append(", ");
        stringBuilder.append("lastname:");
        stringBuilder.append(lastName);
        stringBuilder.append(", ");
        stringBuilder.append("email:");
        stringBuilder.append(email);
        stringBuilder.append(" }");

        return stringBuilder.toString();
    }
}
