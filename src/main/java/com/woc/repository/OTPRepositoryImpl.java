package com.woc.repository;

import com.woc.entity.OTP;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OTPRepositoryImpl implements OTPRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addOTP(OTP otp) {
        entityManager.persist(otp);
    }

    @Override
    public OTP getOTP(String phoneNumber) {
        List<OTP> otpList = entityManager.createNativeQuery("select * from OTP otp where otp.phone = " + phoneNumber, OTP.class).getResultList();
        if(otpList.size()==0) return null;

        return otpList.get(0);
    }

    @Override
    public void removeOTP(OTP otp) {
        String phoneNumber= otp.getPhone();
        entityManager.createNativeQuery("delete from OTP otp where otp.phone = " + phoneNumber);
    }
}
