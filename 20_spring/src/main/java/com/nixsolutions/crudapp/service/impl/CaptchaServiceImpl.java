package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("secret_key")
    private String secretKey;

    @Override
    public boolean verify(String captcha) {
        if (captcha == null || "".equals(captcha)) {
            return false;
        }
        try {
            URL obj = new URL(
                    "https://www.google.com/recaptcha/api/siteverify");
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + secretKey + "&response=" + captcha;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            JsonReader jsonReader = Json.createReader(
                    new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}