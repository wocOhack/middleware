package com.woc.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.woc.dto.PhoneVerificationCompletionRequest;
import com.woc.dto.PhoneVerificationInitiationRequest;
import com.woc.entity.OTP;
import com.woc.repository.OTPRepository;
import com.woc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class OTPService {

    @Autowired
    OTPRepository otpRepository;

    private static final String FORMATTED_URL= "http://203.212.70.200/smpp/sendsms?username=ramk76654&password=unique@1234&to=%s&from=UNIQUS&udh=&text=%s&dlr-mask=19&dlr-url";
    private static final int NUMBER_OF_RETRY =3;
    private static final int OTP_LENGTH =4;
    private static final String USER_AGENT = "Mozilla/5.0";

    public Boolean initiateVerification(final PhoneVerificationInitiationRequest phoneVerificationInitiationRequest) {
        String otp=generateOTP();
        String phoneNumber=phoneVerificationInitiationRequest.getPhoneNumber();
        storeOTP(phoneNumber,otp);
        for(int i=0;i<NUMBER_OF_RETRY;i++){
            try{
                if(sendOTP(phoneNumber,otp)) return true;
            }
            catch (Exception e){
            }

        }
        return false;

    }

    public Boolean completeVerification(PhoneVerificationCompletionRequest phoneVerificationCompletionRequest) {

        String phoneNumber=phoneVerificationCompletionRequest.getPhoneNumber();
        OTP otp=getOTP(phoneNumber);
        if(verifyOTP(phoneVerificationCompletionRequest,otp)){
            otpRepository.removeOTP(otp);
            return true;
        }
        return false;
    }

    private String generateOTP(){
        StringBuilder otp= new StringBuilder();
        for(int i=0;i<OTP_LENGTH;i++){
            otp.append(((int) (Math.random() * 10)) % 10);
        }
        return otp.toString();
    }

    private void storeOTP(String phoneNumber,String otpValue){
        OTP otp=new OTP();
        otp.setPhone(phoneNumber);
        otp.setOtp(otpValue);
        otp.setCreatedTime(new Date(System.currentTimeMillis()));
        otpRepository.addOTP(otp);
    }

    private OTP getOTP(String phoneNumber){
        OTP otp=otpRepository.getOTP(phoneNumber);
        return otp;
    }

    private Boolean verifyOTP(PhoneVerificationCompletionRequest phoneVerificationCompletionRequest,OTP otp){
        String sentOTP=phoneVerificationCompletionRequest.getOTP();
        String savedOTP=otp.getOtp();
        //can add time check also
        return sentOTP.equalsIgnoreCase(savedOTP);
    }

    private Boolean sendOTP(String phoneNumber,String otp) throws IOException {

        URL url = new URL(String.format(FORMATTED_URL,phoneNumber,otp));

        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;

        httpURLConnection.setRequestMethod("POST"); // PUT is another valid option
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // success
            // print result
            String response=getResposeBody(httpURLConnection);
            System.out.println(response);

            if(response.equalsIgnoreCase("sent")) return true;
            else return false;
        } else {
            return false;
        }
    }

    public static void main(String args[]) throws IOException {
        OTPService otpService=new OTPService();
        otpService.sendOTP("8971728134","1234");
    }

    private String getResposeBody(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
