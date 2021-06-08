package com.example.locationtrackk.currentlocation;

import com.google.android.gms.maps.model.LatLng;

class CurrentLocationObserver extends Observer {

    private LatLng latLng;

    public CurrentLocationObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        latLng = this.subject.getState();
    }
}
