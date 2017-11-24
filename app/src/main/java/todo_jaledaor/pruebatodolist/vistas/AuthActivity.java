package todo_jaledaor.pruebatodolist.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import todo_jaledaor.pruebatodolist.R;
import todo_jaledaor.pruebatodolist.fragmentos.LoginFragment;
import todo_jaledaor.pruebatodolist.fragmentos.RecordatorioFragment;
import todo_jaledaor.pruebatodolist.fragmentos.RegistroFragment;

public class AuthActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteraction,
        RegistroFragment.OnRegistroInteractionListener, RecordatorioFragment.OnRecordatorioInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initFragment();
    }

    private void initFragment() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameAuthActivity, LoginFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void finalizarLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void irARegistro() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameAuthActivity, RegistroFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void irARecordar() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameAuthActivity, RecordatorioFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void irALogin() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameAuthActivity, LoginFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void finalizarRecordatorio() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.frameAuthActivity, LoginFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void finalizarRegistro() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
