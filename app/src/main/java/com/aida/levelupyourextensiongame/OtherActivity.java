package com.aida.levelupyourextensiongame;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OtherActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        DomesticAnimal domesticAnimal = new DomesticAnimal(
                "1234", "Cow", "Almaty", Type.CATTLE,
                "someurl", "This is very long des",
                "this is very short des"
        );

        Animal animal = MappersKt.toAnimal(domesticAnimal);

    }
}
