package resultStructureModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amirEbrahimi.um@gmail.com
 */
public class Entry {
    
    public String classId;
    public String    role;

    public Entry(String classId, String role) {
        this.classId = classId;
        this.role = role;
    }

    public Entry() {
    }

    
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
}
