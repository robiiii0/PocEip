import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  tasks = [
    { title: 'Task 1', done: false },
    { title: 'Task 2', done: false },
    { title: 'Task 3', done: false },
  ];

  toggleDone(task: any) {
    task.done = !task.done;
  }

  get doneCount() {
    return this.tasks.filter((t) => t.done).length;
  }
}
