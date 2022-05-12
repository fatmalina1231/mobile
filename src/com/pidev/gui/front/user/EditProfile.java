package com.pidev.gui.front.user;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.pidev.MainApp;
import com.pidev.entities.User;
import com.pidev.services.UserService;

public class EditProfile extends Form {


    User currentUser;

    Label firstNameLabel, lastNameLabel, emailLabel, usernameLabel, createdAtLabel;
    TextField
            firstNameTF,
            lastNameTF,
            emailTF,
            usernameTF;
    PickerComponent createdAtTF;


    Button manageButton;

    Form previous;

    public EditProfile(Form previous) {
        super("Modifier mon profil", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {
        currentUser = MainApp.getSession();


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

        createdAtTF = PickerComponent.createDate(null).label("CreatedAt");


        firstNameTF.setText(currentUser.getFirstName());
        lastNameTF.setText(currentUser.getLastName());
        emailTF.setText(currentUser.getEmail());
        usernameTF.setText(currentUser.getUsername());
        createdAtTF.getPicker().setDate(currentUser.getCreatedAt());


        manageButton = new Button("Modifier");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                firstNameLabel, firstNameTF,
                lastNameLabel, lastNameTF,
                emailLabel, emailTF,
                usernameLabel, usernameTF,
                createdAtTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            if (controleDeSaisie()) {
                int responseCode = UserService.getInstance().edit(
                        new User(
                                currentUser.getId(),


                                firstNameTF.getText(),
                                lastNameTF.getText(),
                                emailTF.getText(),
                                usernameTF.getText(),
                                "ROLE_USER",
                                "",
                                createdAtTF.getPicker().getDate()

                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Profil modifié avec succes", new Command("Ok"));
                    MainApp.setSession(new User(
                            currentUser.getId(),


                            firstNameTF.getText(),
                            lastNameTF.getText(),
                            emailTF.getText(),
                            usernameTF.getText(),
                            "ROLE_USER",
                            "",
                            createdAtTF.getPicker().getDate()
                    ));
                    showBackAndRefresh();
                } else {
                    Dialog.show("Erreur", "Erreur de modification de user. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private void showBackAndRefresh() {
        ((MyProfile) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (firstNameTF.getText().equals("")) {
            Dialog.show("Avertissement", "FirstName vide", new Command("Ok"));
            return false;
        }


        if (lastNameTF.getText().equals("")) {
            Dialog.show("Avertissement", "LastName vide", new Command("Ok"));
            return false;
        }


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Email vide", new Command("Ok"));
            return false;
        }


        if (usernameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Username vide", new Command("Ok"));
            return false;
        }

        if (createdAtTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la createdAt", new Command("Ok"));
            return false;
        }


        return true;
    }
}