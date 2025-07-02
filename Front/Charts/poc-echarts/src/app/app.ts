import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import * as echarts from 'echarts';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements AfterViewInit {
  ngAfterViewInit() {
    const chartDom = document.getElementById('main')!;
    const myChart = echarts.init(chartDom);
    const option = {
      title: { text: 'ECharts POC Angular' },
      tooltip: {},
      xAxis: { data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'] },
      yAxis: {},
      series: [
        {
          type: 'bar',
          data: [120, 200, 150, 80, 70],
        },
      ],
    };
    myChart.setOption(option);
  }
}
