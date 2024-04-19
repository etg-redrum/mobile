package ru.mirea.shchukin.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SensorFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private TextView altitudeText, effectText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        altitudeText = view.findViewById(R.id.altitudeText);
        effectText = view.findViewById(R.id.effectText);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            float pressure = event.values[0];
            float altitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure);
            altitudeText.setText(String.format("Высота: %.2f метров", altitude));

            if (altitude > 2500) {
                effectText.setText("Внимание: возможны проблемы с дыханием и головная боль на высоте!");
            } else {
                effectText.setText("На данной высоте серьезных влияний на организм обычно нет.");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Метод для отслеживания изменения точности датчиков
    }
}
