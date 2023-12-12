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

import inc.sltechnology.olpastpapers.Adapters.MarkingsAdapter;
import inc.sltechnology.olpastpapers.Adapters.PastPapersAdapter;
import inc.sltechnology.olpastpapers.Models.MarkingsModel;
import inc.sltechnology.olpastpapers.Models.PastPapersModel;
import inc.sltechnology.olpastpapers.R;
public class MarkingFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference();

    private List<MarkingsModel> markingsModels;

    private RecyclerView recyclerView;

    private MarkingsAdapter markingsAdapter;

    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_marking, container, false);

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

        loadMarkings();

    }

    private void loadMarkings() {

        DatabaseReference MarkingsRef = database.getReference("marking_schemes"); //***
        recyclerView = requireView().findViewById(R.id.recyclerMarking); //***

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager); //***

        markingsModels = new ArrayList<>(); //***
        markingsAdapter = new MarkingsAdapter(requireContext(),markingsModels); //***
        recyclerView.setAdapter(markingsAdapter); //***

        MarkingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot contentSnapShot:snapshot.getChildren()){

                    MarkingsModel markingsModel = contentSnapShot.getValue(MarkingsModel.class); //***
                    markingsModels.add(markingsModel); //***
                    alertDialog.dismiss();

                }

                markingsAdapter.notifyDataSetChanged(); //***

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

}