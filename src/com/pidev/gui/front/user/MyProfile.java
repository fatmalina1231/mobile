package com.pidev.gui.front.user;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.pidev.MainApp;
import com.pidev.entities.User;

import java.text.SimpleDateFormat;

public class MyProfile extends Form {


    public static User currentUser = null;
    Button editProfileBtn;
    Label firstNameLabel, lastNameLabel, emailLabel, usernameLabel, rolesLabel, passwordLabel, createdAtLabel;

    public MyProfile(Form previous) {
        super("Mon profil", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {

        currentUser = MainApp.getSession();

        editProfileBtn = new Button("Modifier mon profil");
        editProfileBtn.setUIID("buttonWhiteCenter");

        this.add(editProfileBtn);

        this.add(makeUserModel(currentUser));
    }

    private void addActions() {
        editProfileBtn.addActionListener(action -> {
            currentUser = null;
            new EditProfile(this).show();
        });
    }

    private Component makeUserModel(User user) {
        Container userModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        userModel.setUIID("containerRounded");


        firstNameLabel = new Label("FirstName : " + user.getFirstName());
        firstNameLabel.setUIID("labelDefault");

        lastNameLabel = new Label("LastName : " + user.getLastName());
        lastNameLabel.setUIID("labelDefault");

        emailLabel = new Label("Email : " + user.getEmail());
        emailLabel.setUIID("labelDefault");

        usernameLabel = new Label("Username : " + user.getUsername());
        usernameLabel.setUIID("labelDefault");

        rolesLabel = new Label("Roles : " + user.getRoles());
        rolesLabel.setUIID("labelDefault");

        passwordLabel = new Label("Password : " + user.getPassword());
        passwordLabel.setUIID("labelDefault");

        createdAtLabel = new Label("CreatedAt : " + new SimpleDateFormat("dd-MM-yyyy").format(user.getCreatedAt()));
        createdAtLabel.setUIID("labelDefault");


        userModel.addAll(
                firstNameLabel, lastNameLabel, emailLabel, usernameLabel, rolesLabel, passwordLabel, createdAtLabel

        );

        return userModel;
    }
}