package com.example.livedatapractica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.livedatapractica.databinding.FragmentAnimalBinding;

public class AnimalFragment extends Fragment {
    private FragmentAnimalBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentAnimalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnimalViewModel animalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);

        animalViewModel.getAnimal().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Glide.with(AnimalFragment.this).load(integer).into(binding.image);
            }
        });

        animalViewModel.getVeces().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("CAMBIO DE ANIMAL")){
                    binding.cambio.setVisibility(View.VISIBLE);
                } else {
                    binding.cambio.setVisibility(View.INVISIBLE);
                }
                binding.time.setText(s);
            }
        });
    }
}