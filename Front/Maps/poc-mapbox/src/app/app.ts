import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import mapboxgl from 'mapbox-gl';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements AfterViewInit {
  ngAfterViewInit() {
    mapboxgl.accessToken = 'pk.eyJ1Ijoicm9ienp6enoiLCJhIjoiY21hM3B1bTQwMWZjdDJqc2Y3aWNlMXprNSJ9.hc5KleiNnizX6R_Vew3Qfw';

    const map = new mapboxgl.Map({
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [4.8357, 45.7640],
      zoom: 12,
    });

    new mapboxgl.Marker({ color: '#e63946' })
      .setLngLat([4.8357, 45.7640])
      .addTo(map);
  }
}
