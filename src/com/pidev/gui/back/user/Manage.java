package com.pidev.gui.back.user;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.pidev.entities.User;
import com.pidev.services.UserService;
import com.pidev.utils.AlertUtils;

public class Manage extends Form {


    User currentUser;

    TextField firstNameTF;
    TextField lastNameTF;
    TextField emailTF;
    TextField usernameTF;
    TextField rolesTF;
    TextField passwordTF;
    Label firstNameLabel;
    Label lastNameLabel;
    Label emailLabel;
    Label usernameLabel;
    Label rolesLabel;
    Label passwordLabel;
    PickerComponent createdAtTF;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentUser == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentUser = ShowAll.currentUser;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {


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


        rolesLabel = new Label("Roles : ");
        rolesLabel.setUIID("labelDefault");
        rolesTF = new TextField();
        rolesTF.setHint("Tapez le roles");


        passwordLabel = new Label("Password : ");
        passwordLabel.setUIID("labelDefault");
        passwordTF = new TextField();
        passwordTF.setHint("Tapez le password");


        createdAtTF = PickerComponent.createDate(null).label("CreatedAt");


        if (currentUser == null) {


            manageButton = new Button("Ajouter");
        } else {
            firstNameTF.setText(currentUser.getFirstName());
            lastNameTF.setText(currentUser.getLastName());
            emailTF.setText(currentUser.getEmail());
            usernameTF.setText(currentUser.getUsername());
            rolesTF.setText(currentUser.getRoles());
            passwordTF.setText(currentUser.getPassword());
            createdAtTF.getPicker().setDate(currentUser.getCreatedAt());


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                firstNameLabel, firstNameTF,
                lastNameLabel, lastNameTF,
                emailLabel, emailTF,
                usernameLabel, usernameTF,
                rolesLabel, rolesTF,
                passwordLabel, passwordTF,
                createdAtTF,

                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentUser == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = UserService.getInstance().add(
                            new User(


                                    firstNameTF.getText(),
                                    lastNameTF.getText(),
                                    emailTF.getText(),
                                    usernameTF.getText(),
                                    rolesTF.getText(),
                                    passwordTF.getText(),
                                    createdAtTF.getPicker().getDate()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("User ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de user. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = UserService.getInstance().edit(
                            new User(
                                    currentUser.getId(),


                                    firstNameTF.getText(),
                                    lastNameTF.getText(),
                                    emailTF.getText(),
                                    usernameTF.getText(),
                                    rolesTF.getText(),
                                    passwordTF.getText(),
                                    createdAtTF.getPicker().getDate()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("User modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de user. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
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


        if (rolesTF.getText().equals("")) {
            Dialog.show("Avertissement", "Roles vide", new Command("Ok"));
            return false;
        }


        if (passwordTF.getText().equals("")) {
            Dialog.show("Avertissement", "Password vide", new Command("Ok"));
            return false;
        }


        if (createdAtTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la createdAt", new Command("Ok"));
            return false;
        }


        return true;
    }
}