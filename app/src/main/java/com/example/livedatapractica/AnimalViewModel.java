package com.example.livedatapractica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class AnimalViewModel extends AndroidViewModel {
    Animal animal;

    LiveData<Integer> animalLiveData;
    LiveData<String> vecesLiveData;

    public AnimalViewModel(@NonNull Application application) {
        super(application);

        animal = new Animal();

        animalLiveData = Transformations.switchMap(animal.orderLiveData, new Function<String, LiveData<Integer>>() {

            String animalAnterior;

            @Override
            public LiveData<Integer> apply(String input) {

                String animal = input.split(":")[0];

                if (!animal.equals(animalAnterior)) {
                    animalAnterior = animal;
                    int image;
                    switch (animal){
                        case "ANIMAL1":
                        default:
                            image = R.drawable.animal1;
                            break;
                        case "ANIMAL2":
                            image = R.drawable.animal2;
                            break;
                        case "ANIMAL3":
                            image = R.drawable.animal3;
                            break;
                        case "ANIMAL4":
                            image = R.drawable.animal4;
                            break;
                    }
                    return new MutableLiveData<>(image);
                }
                return null;
            }
        });

        vecesLiveData = Transformations.switchMap(animal.orderLiveData, new Function<String, LiveData<String>>() {
            @Override
            public LiveData<String> apply(String input) {
                return new MutableLiveData<>(input.split(":")[1]);
            }
        });
    }

    LiveData<Integer> getAnimal(){
        return animalLiveData;
    }

    LiveData<String> getVeces(){
        return vecesLiveData;
    }
}
