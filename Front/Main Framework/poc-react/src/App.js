import { useState } from 'react';
import './App.css'

export default function App() {
  const [tasks, setTasks] = useState([
    { title: 'Task 1', done: false },
    { title: 'Task 2', done: false },
    { title: 'Task 3', done: false },
  ]);

  const toggleDone = (index) => {
    const updated = [...tasks];
    updated[index].done = !updated[index].done;
    setTasks(updated);
  };

  const doneCount = tasks.filter((t) => t.done).length;

  return (
      <div className="container">
          <div className="subContainer">
              <h1>Task List React</h1>
              <ul>
                  {tasks.map((task, i) => (
                      <li key={i}>
                      <span style={{ textDecoration: task.done ? 'line-through' : 'none' }}>
                          {task.title}
                      </span>
                      <button onClick={() => toggleDone(i)}>
                          {task.done ? 'Undo' : 'Done'}
                      </button>
                  </li>
              ))}
            </ul>
            <p>Tâches terminées : {doneCount}</p>
          </div>
      </div>
  );
}
