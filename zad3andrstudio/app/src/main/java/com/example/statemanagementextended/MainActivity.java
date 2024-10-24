package com.example.statemanagementextended;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Switch Motyw;
    private CheckBox Opcja;
    private TextView Licznik, OpcjaText;
    private StateViewModel stateViewModel;

    public static class StateViewModel extends ViewModel {
        private int count;
        private boolean zazOpcja;
        private boolean CiemnyMotyw;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isCheckBoxChecked() {
            return zazOpcja;
        }

        public void setCheckBoxChecked(boolean checkBoxChecked) {
            zazOpcja = checkBoxChecked;
        }

        public boolean isSwitchChecked() {
            return CiemnyMotyw;
        }

        public void setSwitchChecked(boolean switchChecked) {
            CiemnyMotyw = switchChecked;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateViewModel = new ViewModelProvider(this).get(StateViewModel.class);

        Motyw = findViewById(R.id.switch2);
        Opcja = findViewById(R.id.checkBox);
        Licznik = findViewById(R.id.textViewCount);
        OpcjaText = findViewById(R.id.textViewOption);
        Button buttonIncrement = findViewById(R.id.buttonIncrement);

        OpcjaText.setVisibility(View.GONE);
        updateUI();

        buttonIncrement.setOnClickListener(v -> {
            stateViewModel.setCount(stateViewModel.getCount() + 1);
            updateUI();
        });

        Motyw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            stateViewModel.setSwitchChecked(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(MainActivity.this, "Ciemny Motyw", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(MainActivity.this, "Jasny Motyw", Toast.LENGTH_SHORT).show();
            }
        });

        Opcja.setOnCheckedChangeListener((buttonView, isChecked) -> {
            stateViewModel.setCheckBoxChecked(isChecked);
            OpcjaText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    private void updateUI() {
        Licznik.setText("Licznik: " + stateViewModel.getCount());
        Opcja.setChecked(stateViewModel.isCheckBoxChecked());
        OpcjaText.setVisibility(stateViewModel.isCheckBoxChecked() ? View.VISIBLE : View.GONE);
        Motyw.setChecked(stateViewModel.isSwitchChecked());
    }
}
