<template>
  <div class="container">
    <div class="subContainer">
    <h1>Task List Vue</h1>
    <ul>
      <li v-for="(task, index) in tasks" :key="index">
        <span :style="{ textDecoration: task.done ? 'line-through' : 'none' }">
          {{ task.title }}
        </span>
        <button @click="toggleDone(index)">
          {{ task.done ? 'Undo' : 'Done' }}
        </button>
      </li>
    </ul>
    <p>Tâches terminées : {{ doneCount }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const tasks = ref([
  { title: 'Task 1', done: false },
  { title: 'Task 2', done: false },
  { title: 'Task 3', done: false },
]);

function toggleDone(index) {
  tasks.value[index].done = !tasks.value[index].done;
}

const doneCount = computed(() => tasks.value.filter((t) => t.done).length);
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.subContainer {
  max-width: 600px;
  padding: 3rem;
  background: #f5f5f5;
  border-radius: 8px;
  font-family: Arial, sans-serif;
  text-align: center;
  color: #333;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}

button {
  margin-left: 10px;
  padding: 4px 10px;
  background: #17a2b8;
  border: none;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background: #117a8b;
}
</style>
