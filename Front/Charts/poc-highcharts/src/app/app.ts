import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements AfterViewInit {
  ngAfterViewInit() {
    Highcharts.chart('container', {
      chart: {
        type: 'column'
      },
      title: {
        text: 'Highcharts POC Angular'
      },
      xAxis: {
        categories: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri']
      },
      yAxis: {
        title: {
          text: 'Values'
        }
      },
      series: [{
        name: 'Data',
        type: 'column',
        data: [120, 200, 150, 80, 70],
      }]
    });
  }
}
