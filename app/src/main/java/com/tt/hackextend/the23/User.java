package com.tt.hackextend.the23;

/**
 * Created by yotamc on 15-Sep-16.
 */
public class User implements Comparable<User>{

    public String name;
    public String phone_number;
    public String email;
    public String city;

    // TODO: skills should be class by itself + more than one for base and want
    public String base_skill;
    public String want_skill;
    public int reputation;

    public User() {
    }





    public User(String name, String phone_number, String email, String city, String base_skill, String want_skill) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.city = city;
        this.base_skill = base_skill;
        this.want_skill = want_skill;
        this.reputation = 0;
    }

    @Override
    public int compareTo(User u) {
        if (this.getReputation()>u.getReputation())
            return -1;
        else if (this.getReputation()<u.getReputation())
            return 1;
        else
            return 0;
    }

    public int getReputation() {
        return reputation;
    }

    public void upReputation() {
        this.reputation = this.reputation + 1;
    }

    @Override
    public String toString() {
        return name + "(" + this.getReputation() + ")" + " from " + city + ", Phone Number:" + phone_number;
    }
}


