package com.semanientreprise.encryptedrealm;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.security.SecureRandom;

import io.realm.Realm;

public class ApplicationClass extends Application {

    public static byte[] key;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        key = new byte[64];
        new SecureRandom().nextBytes(key);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .withEncryptionKey("realEncrypted.realm",key)
                                .build())
                        .build());
    }
}
