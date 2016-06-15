package com.alejoyarce.simplechat.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.alejoyarce.simplechat.R;
import com.alejoyarce.simplechat.contactlist.ContactListActivity;
import com.alejoyarce.simplechat.login.mvp.LoginPresenter;
import com.alejoyarce.simplechat.login.mvp.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.editTxtMail) EditText editTxtMail;
    @Bind(R.id.editTxtPass) EditText editTxtPass;
    @Bind(R.id.btnSignIn) Button btnSignIn;
    @Bind(R.id.btnSignUp) Button btnSignUp;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer) RelativeLayout layoutMainContainer;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    public void onClickSignIn() {

    }

    @OnClick(R.id.btnSignUp)
    public void onClickSignUp() {

    }



    @Override
    public void enableInputs() {
        setInputsStatus(true);
    }

    @Override
    public void disableInputs() {
        setInputsStatus(true);
    }

    private void setInputsStatus(boolean enable) {
        editTxtMail.setEnabled(enable);
        editTxtPass.setEnabled(enable);
        btnSignIn.setEnabled(enable);
        btnSignUp.setEnabled(enable);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.btnSignIn)
    public void handleSignIn() {
        loginPresenter.validateLogin(editTxtMail.getText().toString(), editTxtPass.getText().toString());
    }

    @Override
    @OnClick(R.id.btnSignUp)
    public void handleSignUp() {
        loginPresenter.registerNewUser(editTxtMail.getText().toString(), editTxtPass.getText().toString());
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {
        editTxtPass.setText("");
        String msgError = String.format(getString(R.string.login_error_message_sign_in), error);
        editTxtPass.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(layoutMainContainer, R.string.login_success_message_sign_up, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        editTxtPass.setText("");
        String msgError = String.format(getString(R.string.login_error_message_sign_up), error);
        editTxtPass.setError(msgError);
    }

}
