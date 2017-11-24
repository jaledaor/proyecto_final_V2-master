package todo_jaledaor.pruebatodolist.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import todo_jaledaor.pruebatodolist.R;
import todo_jaledaor.pruebatodolist.presenters.IRecordatorioPresenter;
import todo_jaledaor.pruebatodolist.presenters.RecordatorioPresenter;

public class RecordatorioFragment extends Fragment implements IRecordatorioFragmentView {

    @BindView(R.id.txt_rec_email)
    EditText txt_rec_email;

    @BindView(R.id.btnRecordar)
    Button btnRecordar;

    @BindView(R.id.progressRec)
    ProgressBar progressRec;

    @BindView(R.id.txtVolverLogin)
    TextView txtVolverLogin;

    private IRecordatorioPresenter recordatorioPresenter;

    private OnRecordatorioInteractionListener mListener;

    public RecordatorioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecordatorioFragment newInstance() {
        RecordatorioFragment fragment = new RecordatorioFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recordatorio, container, false);
        ButterKnife.bind(this, view);

        recordatorioPresenter = new RecordatorioPresenter(this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecordatorioInteractionListener) {
            mListener = (OnRecordatorioInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void habilitarControles() {
        btnRecordar.setEnabled(true);
        txt_rec_email.setEnabled(true);
    }

    @Override
    public void deshabilitarControles() {
        btnRecordar.setEnabled(false);
        txt_rec_email.setEnabled(false);
    }

    @Override
    public void mostrarProgress() {
        progressRec.setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarProgress() {
        progressRec.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnRecordar)
    @Override
    public void recordar() {
        String email = txt_rec_email.getText().toString();
        recordatorioPresenter.recordar(email);
    }

    @OnClick(R.id.txtVolverLogin)
    public void ClicVolver() {
        // Crea el nuevo fragmento y la transacción.
        Fragment nuevoFragmento = new LoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAuthActivity, nuevoFragmento);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void mostrarError(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void irALogin() {
        if (mListener != null) {
            mListener.irALogin();
        }
    }

    @Override
    public void finalizarRecordatorio() {
        if (mListener != null) {
            mListener.finalizarRecordatorio();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecordatorioInteractionListener {
        // TODO: Update argument type and name
        void irALogin();

        void finalizarRecordatorio();
    }
}
