package com.example.authorizationtemplate.presentation.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.authorizationtemplate.R;
import com.example.authorizationtemplate.di.ComponentManager;
import com.example.authorizationtemplate.utils.KeyboardUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.edUserEmail)
    EditText editTextUserEmail;
    @BindView(R.id.edPassword)
    EditText editTextPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRouteToRegistration)
    Button btnRouteToRegistration;
    @BindView(R.id.tvErrorLogin)
    TextView tvErrorLogin;

    @Inject
    LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        injectDependencies();
        setupActionBar();
        tvErrorLogin.setVisibility(View.INVISIBLE);
        editTextUserEmail.addTextChangedListener(new LoginTextWatcher(editTextUserEmail));
        editTextPassword.addTextChangedListener(new LoginTextWatcher(editTextPassword));
    }

    private void injectDependencies() {
        ComponentManager.getInstance().addLoginComponent(this, this);
        ComponentManager.getInstance().getLoginComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComponentManager.getInstance().clearLoginComponent();
        presenter.destroy();
    }

    @OnClick({R.id.btnLogin, R.id.btnRouteToRegistration})
    public void onClick(View v) {
        KeyboardUtils.hideKeyboard(this);
        switch (v.getId()) {
            case R.id.btnRouteToLogin:
                presenter.registrationButtonClicked();
                break;
            case R.id.btnRegistration: {
                String username = editTextUserEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!(username.matches("")) && !(password.matches("")))
                    presenter.setAuthData(username, password);
                break;
            }
        }
    }

    private void setupActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void showErrorMessage(String msg) {
        tvErrorLogin.setVisibility(View.VISIBLE);
        tvErrorLogin.setText(msg);
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public class LoginTextWatcher implements TextWatcher {

        private View view;

        private LoginTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            tvErrorLogin.setVisibility(View.INVISIBLE);
        }
    }
}
