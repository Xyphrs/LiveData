package com.example.livedatapractica;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Animal {
    interface AnimalListener {
        void whenOrder(String order);
    }

    LiveData<String> orderLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            startAnimal(new AnimalListener() {
                @Override
                public void whenOrder(String order) {
                    postValue(order);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            stopAnimal();
        }
    };

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> animaleando;

    void startAnimal(AnimalListener animalListener) {
        if (animaleando == null  || animaleando.isCancelled()) {
            animaleando = scheduler.scheduleAtFixedRate(new Runnable() {
                int animalSeleccion;
                int vecesAnimal = - 1;

                @Override
                public void run() {
                    if (vecesAnimal < 0) {
                        vecesAnimal = random.nextInt(3) + 3;
                        animalSeleccion = random.nextInt(5)+1;
                    }
                    animalListener.whenOrder("ANIMAL" + animalSeleccion + ":" + (vecesAnimal == 0 ? "CAMBIO DE ANIMAL" : vecesAnimal));
                    vecesAnimal--;
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    void stopAnimal() {
        if (animaleando != null){
            animaleando.cancel(true);
        }
    }
}
