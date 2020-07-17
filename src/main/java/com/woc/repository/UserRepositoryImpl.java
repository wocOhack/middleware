package com.woc.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.woc.entity.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        List<User> users = entityManager.createNamedQuery("User.findAll").getResultList();
        return users;
    }

    @Override
    @Transactional
    public User createNewUser(User newUser) {
        newUser.setRegistrationDate(getCurrentDate());
        entityManager.persist(newUser);
        return newUser;
    }

    private Date getCurrentDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        return currentTime;
    }

    @Transactional
    @Override
    public User addUser(User u) {
        // TODO Auto-generated method stub
        entityManager.persist(u);
        return u;
    }

    @Transactional
    @Override
    public long updateUser(String userName, String emailAdd, String phoneNum, long userId) {
        System.out.println("phone Number : " + phoneNum);

        if (userName != "" && userName != null && (emailAdd != "" && emailAdd != null)) {
            if (phoneNum != "" && phoneNum != null) {
                System.out.println("phoneNum " +  phoneNum);
                Query q = entityManager.createNativeQuery(
                        "Update User u set u.email = :emailAdd, u.Name = :userName  where u.phone = " + phoneNum);
                q.setParameter("emailAdd", emailAdd);
                q.setParameter("userName", userName);
                long rowsUpdated = q.executeUpdate();
                System.out.println("rowsUpdated : " + rowsUpdated);
                return rowsUpdated;
            }
            if (userId != 0) {
                System.out.println("userId " +  userId);
                Query q = entityManager.createNativeQuery(
                        "Update User u set u. email = :emailAdd,  u.Name = :userName where u.id = " + userId);
                q.setParameter("emailAdd", emailAdd);
                q.setParameter("userName", userName);
                long rowsUpdated = q.executeUpdate();
                System.out.println("rowsUpdated : " + rowsUpdated);
                return rowsUpdated;
            }

        } else if (userName != "" && userName != null) {
            if (phoneNum != "" && phoneNum != null) {
                Query q = entityManager
                        .createNativeQuery("Update User u set u.Name = :userName where u.phone = " + phoneNum);
                q.setParameter("userName", userName);
                long rowsUpdated = q.executeUpdate();
                System.out.println("rowsUpdated : " + rowsUpdated);
                return rowsUpdated;
            }
            if (userId != 0) {
                System.out.println("this case to be updated");
                Query q = entityManager
                        .createNativeQuery("Update User u set u.name =  :userName where u.id = " + userId);
                q.setParameter("userName", userName);
                long rowsUpdated = q.executeUpdate();
                System.out.println("rowsUpdated : " + rowsUpdated);
                return rowsUpdated;
            }
        } else if (emailAdd != "" && emailAdd != null) {
            if (phoneNum != "") {
                Query q = entityManager
                        .createNativeQuery("Update User u set u.email = :emailAdd  where u.phone = " + phoneNum);
                q.setParameter("emailAdd", emailAdd);
                long rowsUpdated = q.executeUpdate();
                System.out.println("rowsUpdated : " + rowsUpdated);
                return rowsUpdated;
            }
            if (userId != 0) {
                Query q = entityManager
                        .createNativeQuery("Update User u set u. email = :emailAdd where u.id = " + userId);
                q.setParameter("emailAdd", emailAdd);
                long rowsUpdated = q.executeUpdate();
                System.out.println("rowsUpdated : " + rowsUpdated);
                return rowsUpdated;
            }
        }
        return 0l;
    }

    @Override
    public User findByID(long id) {
        List<User> users = entityManager.createNamedQuery("User.findById").setParameter(1, id).getResultList();
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;

    }
}
