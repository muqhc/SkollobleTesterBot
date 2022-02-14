package io.github.muqhc.skblebot.db

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.InputStream

fun dbAPIInitialize(dbCredentialJson: InputStream, dbUrl: String): Firestore =
    FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(dbCredentialJson))
        .setDatabaseUrl(dbUrl)
        .build().let { options ->
            FirebaseApp.initializeApp(options).let { app ->
                FirestoreClient.getFirestore(app)
            }
        }
