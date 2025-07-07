package sst.swam.grocify.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import sst.swam.grocify.dao.AdminDao;
import sst.swam.grocify.model.Admin;

@ApplicationScoped
public class AdminService {

    @Inject
    AdminDao adminDao;

    public Admin findById(Long id) {
        return adminDao.findById(id);
    }

    public Admin findByEmail(String email) {
        return adminDao.findByEmail(email);
    }

    public void save(Admin admin) {
        adminDao.save(admin);
    }

    public Admin update(Admin admin) {
        return adminDao.update(admin);
    }

    public void delete(Long id) {
        adminDao.delete(id);
    }
}
