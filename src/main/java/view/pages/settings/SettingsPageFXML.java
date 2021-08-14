package view.pages.settings;

import event.events.settings.SettingsForm;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.ImageUtil;

import java.util.Date;

public class SettingsPageFXML
{
    private final SettingsPageListener listener = new SettingsPageListener();

    public Text messageText;

    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public TextField nameTextField;
    public TextField emailTextField;
    public TextField phoneNumberTextField;
    public DatePicker birthDatePicker;
    public TextField bioTextField;
    public TextField pictureTextField;
    public ChoiceBox<String> privacyChoiceBox;
    public ChoiceBox<String> infoStateChoiceBox;
    public ChoiceBox<String> lastSeenChoiceBox;

    public Button deleteAccountButton;
    public Button deactivationButton;
    
    public CheckBox usernameCheckBox;
    public CheckBox passwordCheckBox;
    public CheckBox nameCheckBox;
    public CheckBox emailCheckBox;
    public CheckBox birthdateCheckBox;
    public CheckBox bioCheckBox;
    public CheckBox pictureCheckBox;
    public CheckBox privacyCheckBox;
    public CheckBox infoStateCheckBox;
    public CheckBox phoneNumberCheckBox;
    public CheckBox lastSeenCheckBox;

    public Button editButton;

    public void setMessageText(String message, boolean ok)
    {
        messageText.setText(message);
        messageText.setVisible(true);
        messageText.setFill(ok ? Color.GREEN : Color.RED);
    }

    public void usernameCheckBox()
    {
        usernameTextField.setDisable(!usernameCheckBox.isSelected());
    }

    public void passwordCheckBox()
    {
        passwordTextField.setDisable(!passwordCheckBox.isSelected());
    }

    public void nameCheckBox()
    {
        nameTextField.setDisable(!nameCheckBox.isSelected());
    }

    public void emailCheckBox()
    {
        emailTextField.setDisable(!emailCheckBox.isSelected());
    }

    public void phoneNumberCheckBox()
    {
        phoneNumberTextField.setDisable(!phoneNumberCheckBox.isSelected());
    }

    public void birthdateCheckBox()
    {
        birthDatePicker.setDisable(!birthdateCheckBox.isSelected());
    }

    public void bioCheckBox()
    {
        bioTextField.setDisable(!bioCheckBox.isSelected());
    }

    public void pictureCheckBox()
    {
        pictureTextField.setDisable(!pictureCheckBox.isSelected());
    }

    public void privacyCheckBox()
    {
        privacyChoiceBox.setDisable(!privacyCheckBox.isSelected());
    }

    public void infoStateCheckBox()
    {
        infoStateChoiceBox.setDisable(!infoStateCheckBox.isSelected());
    }

    public void lastSeenCheckBox()
    {
        lastSeenChoiceBox.setDisable(!lastSeenCheckBox.isSelected());
    }

    public void edit()
    {
        SettingsForm form = new SettingsForm(editButton);

        if (usernameCheckBox.isSelected())
        {
            form.setUsername(usernameTextField.getText());
        }
        if (passwordCheckBox.isSelected())
        {
            form.setPassword(passwordTextField.getText());
        }
        if (nameCheckBox.isSelected())
        {
            form.setName(nameTextField.getText());
        }
        if (emailCheckBox.isSelected())
        {
            form.setEmail(emailTextField.getText());
        }
        if (phoneNumberCheckBox.isSelected())
        {
            form.setPhoneNumber(phoneNumberTextField.getText());
        }
        if (bioCheckBox.isSelected())
        {
            form.setBio(bioTextField.getText());
        }
        if (pictureCheckBox.isSelected())
        {
            if (pictureTextField.getText().equals(""))
            {
                form.setPicture("");
            }
            else
            {
                form.setPicture(ImageUtil.imageToString(pictureTextField.getText()));
            }
        }
        if (birthdateCheckBox.isSelected() && birthDatePicker.getValue() != null)
        {
            Date birthdate = java.sql.Date.valueOf(birthDatePicker.getValue());
            form.setBirthDate(birthdate);
        }
        if (privacyCheckBox.isSelected())
        {
            form.setPrivateState(privacyChoiceBox.getSelectionModel().getSelectedItem().equals("private"));
        }
        if (infoStateCheckBox.isSelected())
        {
            form.setInfoState(infoStateChoiceBox.getSelectionModel().getSelectedItem().equals("public"));
        }
        if (lastSeenCheckBox.isSelected())
        {
            int lastSeenState;
            switch (lastSeenChoiceBox.getSelectionModel().getSelectedItem())
            {
                case "no one":
                default:
                    lastSeenState = 0;
                    break;
                case "followings":
                    lastSeenState = 1;
                    break;
                case "everyone":
                    lastSeenState = 2;
                    break;
            }
            form.setLastSeenState(lastSeenState);
        }
        this.listener.eventOccurred(form);
    }

    public void deleteAccount()
    {
        this.listener.eventOccurred(new SettingsForm(deleteAccountButton));
    }

    public void deactivate()
    {
        this.listener.eventOccurred(new SettingsForm(deactivationButton));
    }
}
