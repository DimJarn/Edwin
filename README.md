# Edwin
PFE

Edwin, l'application chirugicale qui vous accompagne en chrirugie thoracique.
Projet de fin d'étude réalisé au sein de l'ESEO pour l'année scolaire 2018 - 2019 par 3 étudiants.

Jarneau Dimitri
Jaunay Jules
Nassim Messaoudi

*****************************************


Instruction d'importation du projet sur AS:


*****************************************
1 - Version Android studio : 3.2 Minimum
*****************************************
2 - SDK 5.0 Version Lollipops (API 21)
*****************************************
3 - Version de Gradle.3.2 (minimum)
*****************************************


--> Clique droit sur le nom du projet 
--> Module settings
--> Project : Gradle version
              Android plugin version
              
Build and run sur l'émulateur

*****************************************

Comment utiliser l'application ? 

- Selectionner une fiche:
![enregistrer_fiche](https://user-images.githubusercontent.com/28304015/51375148-66809500-1b05-11e9-89a1-68d64944495a.gif)

- Comment chercher un mot du glossaire:
![chercher_mot](https://user-images.githubusercontent.com/28304015/51374993-eeb26a80-1b04-11e9-9f1e-6078775aaf36.gif)


**********************************Informations sur les modules externes ******************************

Pour que l'appli fonctionne sur A.S il faut importer les modules suivant:

*****************TOASTY*****************************************************************************

https://github.com/GrenderG/Toasty --> pour des toats + beau style Bootstrap

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" } --> ceci a rajouter dans build.gradle (Project Edwin)
    }
}

et
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation 'com.android.support:support-v4:27.1.1'
    implementation 'com.android.support:design:27.1.1'
    implementation 'android.arch.persistence.room:runtime:1.1.1'
    implementation 'com.android.support:cardview-v7:27.1.1'
    implementation 'com.google.code.gson:gson:2.8.2'
    implementation 'com.github.GrenderG:Toasty:1.3.0'  --------> ceci dans build.gradle Module app
    annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}

****************************************************************************************************

Expendable View :
- des expandables views différents et facile a implementer
    implementation 'com.ms-square:expandableTextView:0.1.4'
****************************************************************************************************
GIF : 
- intégration de gifs dans l'application
    implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.+'
****************************************************************************************************
