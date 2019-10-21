package com.example.authorizationtemplate.presentation.registration;

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

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.edUserName)
    EditText editTextUserName;
    @BindView(R.id.edUserEmail)
    EditText editTextUserEmail;
    @BindView(R.id.edPassword)
    EditText editTextPassword;
    @BindView(R.id.edConfirmPassword)
    EditText editTextConfirmPassword;
    @BindView(R.id.btnRegistration)
    Button btnRegistration;
    @BindView(R.id.btnRouteToLogin)
    Button btnRouteToLogin;
    @BindView(R.id.tvErrorLogin)
    TextView tvErrorLogin;

    @Inject
    RegistrationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        presenter.create();
        ButterKnife.bind(this);
        injectDependencies();
        setupActionBar();
        setupTextChangedListeners();
        tvErrorLogin.setVisibility(View.INVISIBLE);
    }

    private void injectDependencies() {
        ComponentManager.getInstance().addRegistrationComponent(this, this);
        ComponentManager.getInstance().getRegistrationComponent().inject(this);
    }

    private void setupTextChangedListeners() {
        editTextUserEmail.addTextChangedListener(new RegistrationTextWatcher(editTextUserEmail));
        editTextUserEmail.addTextChangedListener(new RegistrationTextWatcher(editTextUserEmail));
        editTextPassword.addTextChangedListener(new RegistrationTextWatcher(editTextPassword));
        editTextPassword.addTextChangedListener(new RegistrationTextWatcher(editTextPassword));
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
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @OnClick({R.id.btnRegistration, R.id.btnRouteToLogin})
    public void onClick(View v){
        KeyboardUtils.hideKeyboard(this);
        switch (v.getId()){
            case R.id.btnRouteToLogin:
                presenter.loginButtonClicked();
                break;
            case R.id.btnRegistration:
                if(isFieldsEmpty()){
                    tvErrorLogin.setVisibility(View.VISIBLE);
                    tvErrorLogin.setText(getResources().getString(R.string.all_fields_must_be_filled));
                }
                else
                    presenter.setRegistrationData(editTextUserName.getText().toString(),
                            editTextUserEmail.getText().toString(),
                            editTextPassword.getText().toString(),
                            editTextConfirmPassword.getText().toString());
                break;
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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isFieldsEmpty() {
        if(editTextUserName.getText().toString().trim().isEmpty() ||
                editTextUserEmail.getText().toString().trim().isEmpty() ||
                editTextPassword.getText().toString().trim().isEmpty() ||
                editTextConfirmPassword.getText().toString().trim().isEmpty())
            return true;
        return false;
    }

    public class RegistrationTextWatcher implements TextWatcher {

        private View view;

        private RegistrationTextWatcher(View view) {
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
