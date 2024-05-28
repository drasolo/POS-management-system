package Clase;

import java.util.ArrayList;
import java.util.List;

public class AdminRepository {
    private List<Admin> admins;
    private static AdminRepository instance;

    private AdminRepository() {
        admins = new ArrayList<>();
    }

    public static AdminRepository getInstance() {
        if (instance == null) {
            instance = new AdminRepository();
        }
        return instance;
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public Admin getAdminById(String id) {
        for (Admin admin : admins) {
            if (admin.getEmployeeID().equals(id)) {
                return admin;
            }
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        return new ArrayList<>(admins);
    }

    public void updateAdmin(Admin admin) {
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getEmployeeID().equals(admin.getEmployeeID())) {
                admins.set(i, admin);
                break;
            }
        }
    }

    public void deleteAdmin(String id) {
        admins.removeIf(admin -> admin.getEmployeeID().equals(id));
    }
}
