package com.woc.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woc.dto.PINUpdateRequestObject;
import com.woc.dto.RiderSearchCriteria;
import com.woc.entity.Driver;
import com.woc.entity.Rider;
import com.woc.entity.User;

@Repository
public class RiderRepositoryImpl implements RiderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Rider> findAll() {
        List<Rider> riders = entityManager.createNamedQuery("Rider.findAll", Rider.class).getResultList();
        return riders;
    }
    
	
	@Override
	public Rider findByID(long id) {
		List<Rider> riders = entityManager.createNamedQuery("Rider.findById").setParameter(1, id).getResultList();
		if(!riders.isEmpty()) {
			return riders.get(0);
		}
		return null;
		
	}

    @Transactional
    @Override
    public void addRider(Rider r) {
        // TODO Auto-generated method stub
        entityManager.persist(r);
    }

    @Override
    public com.woc.dto.Rider getRider(RiderSearchCriteria searchRider) {
        // TODO Auto-generated method stub
        if (searchRider.getPhoneNumber() != null) {
            String number = searchRider.getPhoneNumber();
            List<User> users = entityManager
                    .createNativeQuery("select * from User u where u.phone = " + number, User.class).getResultList();
            List<com.woc.dto.Rider> allRiders = new ArrayList();
            // for (User u : users) {
            System.out.println("user length : " + users.size());

            if (users.size() == 0) {
                return null;
            }
            User u = users.get(0);
            long userId = u.getId();
            List<Rider> riders = entityManager
                    .createNativeQuery("select * from Rider r where r.user_id = " + userId, Rider.class)
                    .getResultList();
            // for (Rider each : riders) {

            if (riders.size() == 0) {
                return null;
            }

            Rider each = riders.get(0);
            com.woc.dto.Rider r = new com.woc.dto.Rider();
            r.setEmail(u.getEmail());
            r.setName(u.getName());
            r.setPhoneNumber(u.getPhone());
            r.setRiderID(each.getId());
            // allRiders.add(r);
            // }

            // }
            return r;

        } else {
            long riderId = searchRider.getRiderId();
            List<Rider> riders = entityManager
                    .createNativeQuery("select * from Rider r where r.id = " + riderId, Rider.class).getResultList();
            if (riders.size() == 0) {
                return null;
            }
            Rider rider = riders.get(0);
            long userid = rider.getUser_id();

            List<User> users = entityManager
                    .createNativeQuery("select * from User u where u.id = " + userid, User.class).getResultList();
            if (users.size() == 0) {
                return null;
            }
            User u = users.get(0);
            // for (Rider each : riders) {
            com.woc.dto.Rider r = new com.woc.dto.Rider();
            r.setEmail(u.getEmail());
            r.setName(u.getName());
            // r.setPIN(u.get);
            r.setPhoneNumber(u.getPhone());
            r.setRiderID(rider.getId());
            return r;
            // } // return riders;
        }
    }

    @Override
    public void updateRiderPin(PINUpdateRequestObject updateReq) {
        // TODO Auto-generated method stub
        long riderId = updateReq.getRiderID();
        String pin = updateReq.getPIN();
        entityManager.getTransaction().begin();
        Query q = entityManager.createNativeQuery("update Rider r set r.pin = " + pin + " where r.id =" + riderId,
                Rider.class);
        int rowsUpdated = q.executeUpdate();
        System.out.println("updated row : " + rowsUpdated);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
