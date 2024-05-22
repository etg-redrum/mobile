package ru.mirea.shchukin.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.shchukin.mireaproject.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private MyLocationNewOverlay locationNewOverlay;
    private FragmentMapBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setupMap();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setupMap();
        } else {
            Toast.makeText(getContext(), "Permission denied!", Toast.LENGTH_LONG).show();
        }
    }

    private void setupMap() {
        binding.mapView.setMultiTouchControls(true);
        IMapController mapController = binding.mapView.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(new GeoPoint(55.794229, 37.700772));

        locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getContext()), binding.mapView);
        locationNewOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(locationNewOverlay);

        CompassOverlay compassOverlay = new CompassOverlay(getContext(), new InternalCompassOrientationProvider(getContext()), binding.mapView);
        compassOverlay.enableCompass();
        binding.mapView.getOverlays().add(compassOverlay);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(binding.mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        binding.mapView.getOverlays().add(scaleBarOverlay);

        addMarker("Красная площадь\n Тут можно погулять", new GeoPoint(55.753544, 37.621202));
        addMarker("РТУ МИРЭА стромынка, мой любимый вуз\nУчусь тут", new GeoPoint(55.794295, 37.701571));
        addMarker("Рыбу здесь хочу половить\n 52 сома поймал на спинниг)", new GeoPoint(56.071238, 36.801830));
    }

    private void addMarker(String nameMarker, GeoPoint point) {
        Marker marker = new Marker(binding.mapView);
        marker.setPosition(point);
        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker.setTitle(nameMarker);
        marker.setOnMarkerClickListener((marker1, mapView) -> {
            Toast.makeText(getContext(), "Маркер " + nameMarker, Toast.LENGTH_SHORT).show();
            return true;
        });
        binding.mapView.getOverlays().add(marker);
    }
}
