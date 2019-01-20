package com.example.tvbbz.fithub.data;

import java.util.ArrayList;
import java.util.List;

public class StaffProvider {

    public static boolean isStaffMember(String email){
        List<String> members = new ArrayList<>();
        members.add("zacharyparch@yahoo.com");

        for(String member:members){
            if(member.equals(email)){
                return true;
            }
        }

        return false;
    }
}
