package com.pidev.gui;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.pidev.entities.User;
import com.pidev.services.UserService;

public class Register extends Form {


    Label firstNameLabel, lastNameLabel, emailLabel, usernameLabel, passwordLabel, createdAtLabel;
    TextField
            firstNameTF,
            lastNameTF,
            emailTF,
            usernameTF,
    passwordTF;
    PickerComponent createdAtTF;


    Button manageButton;

    Form previous;

    public Register(Form previous) {
        super("Inscription", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {


        Label loginLabel = new Label("Vous avez deja un compte ?");

        Button loginBtn = new Button("Login");
        loginBtn.setUIID("buttonWhiteCenter");
        loginBtn.addActionListener(l -> new com.pidev.gui.Login().show());


        firstNameLabel = new Label("FirstName : ");
        firstNameLabel.setUIID("labelDefault");
        firstNameTF = new TextField();
        firstNameTF.setHint("Tapez le firstName");

        lastNameLabel = new Label("LastName : ");
        lastNameLabel.setUIID("labelDefault");
        lastNameTF = new TextField();
        lastNameTF.setHint("Tapez le lastName");

        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");

        usernameLabel = new Label("Username : ");
        usernameLabel.setUIID("labelDefault");
        usernameTF = new TextField();
        usernameTF.setHint("Tapez le username");

        passwordLabel = new Label("Password : ");
        passwordLabel.setUIID("labelDefault");
        passwordTF = new TextField();
        passwordTF.setHint("Tapez le password");
        createdAtTF = PickerComponent.createDate(null).label("CreatedAt");


        manageButton = new Button("S'inscrire");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                firstNameLabel, firstNameTF,
                lastNameLabel, lastNameTF,
                emailLabel, emailTF,
                usernameLabel, usernameTF,
                passwordLabel, passwordTF,
                createdAtTF,

                manageButton,
                loginLabel, loginBtn
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = UserService.getInstance().add(
                        new User(


                                firstNameTF.getText(),
                                lastNameTF.getText(),
                                emailTF.getText(),
                                usernameTF.getText(),
                                "ROLE_USER",
                                passwordTF.getText(),
                                createdAtTF.getPicker().getDate()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Inscription effectué avec succes", new Command("Ok"));
                    previous.showBack();
                } else if (responseCode == 203) {
                    Dialog.show("Erreur", "Email deja utilisé", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de user. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private boolean controleDeSaisie() {


        if (firstNameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir firstName", new Command("Ok"));
            return false;
        }


        if (lastNameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir lastName", new Command("Ok"));
            return false;
        }


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir email", new Command("Ok"));
            return false;
        }


        if (usernameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir username", new Command("Ok"));
            return false;
        }




        if (passwordTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir password", new Command("Ok"));
            return false;
        }


        if (createdAtTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "CreatedAt vide", new Command("Ok"));
            return false;
        }


        return true;
    }
}