package inc.sltechnology.olpastpapers.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inc.sltechnology.olpastpapers.Adapters.PastPapersAdapter;
import inc.sltechnology.olpastpapers.Models.PastPapersModel;
import inc.sltechnology.olpastpapers.R;

public class PastPapersFragment extends Fragment {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private List<PastPapersModel> pastPapersModels;

    private RecyclerView recyclerView;

    private PastPapersAdapter pastPapersAdapter;

    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_past_papers, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

        loadPastPapers();

    }

    private void loadPastPapers() {

        DatabaseReference PastPapersRef = database.getReference("past_papers"); //***
        recyclerView = requireView().findViewById(R.id.recyclerPastPapers); //***

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager); //***

        pastPapersModels = new ArrayList<>(); //***
        pastPapersAdapter = new PastPapersAdapter(requireContext(),pastPapersModels); //***
        recyclerView.setAdapter(pastPapersAdapter); //***

        PastPapersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot contentSnapShot:snapshot.getChildren()){

                    PastPapersModel pastPapersModel = contentSnapShot.getValue(PastPapersModel.class); //***
                    pastPapersModels.add(pastPapersModel); //***
                    alertDialog.dismiss();

                }

                pastPapersAdapter.notifyDataSetChanged(); //***

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

}