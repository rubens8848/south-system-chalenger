package br.com.forja.bits.donation.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

public class Env {
    private static Env instance = null;

    private final Dotenv env = Dotenv.configure()
//            .directory("./src/main/resources")
//            .filename(".env")
            .load();

    public final String SECURITY_JWT_SECRET = env.get("SECURITY_JWT_SECRET");
    public final String SECURITY_JWT_EXPIRATION = env.get("SECURITY_JWT_EXPIRATION");

    private Env() {
        System.out.println("Dotenv loaded.");
    }

    public static Env getInstance() {
        if (instance == null)
            instance = new Env();
        return instance;
    }

}
