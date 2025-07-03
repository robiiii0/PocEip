import pandas as pd
import time
import torch
import torch.nn as nn
import torch.optim as optim
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score
from sklearn.preprocessing import StandardScaler

print("--- 1. Préparation des données ---")
try:
    data = pd.read_csv('data.csv')
except FileNotFoundError:
    print("ERREUR : Fichier 'data.csv' non trouvé. Renommez votre CSV.")
    exit()

X = data[['nouvelle_neige_cm', 'temperature', 'vent_kmh']]
y = data['risque_eleve']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=42)

print(f"Données chargées : {len(data)} lignes")
print(f"Taille du jeu d'entraînement : {len(X_train)} lignes")
print(f"Taille du jeu de test : {len(X_test)} lignes\n")


print("--- 2. Entraînement avec Scikit-learn (Random Forest) ---")

model_rf = RandomForestClassifier(n_estimators=100, random_state=42)

start_time = time.time()
model_rf.fit(X_train, y_train)
rf_training_time = time.time() - start_time

predictions_rf = model_rf.predict(X_test)
accuracy_rf = accuracy_score(y_test, predictions_rf)

print(f"Code : 2 lignes (création + entraînement)")
print(f"Temps d'entraînement : {rf_training_time:.6f} secondes")
print(f"Précision sur le jeu de test : {accuracy_rf:.2f}")


print("\n--- 3. Entraînement avec PyTorch (Réseau de Neurones) ---")

scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

X_train_tensor = torch.tensor(X_train_scaled, dtype=torch.float32)
y_train_tensor = torch.tensor(y_train.values, dtype=torch.float32).view(-1, 1)
X_test_tensor = torch.tensor(X_test_scaled, dtype=torch.float32)
y_test_tensor = torch.tensor(y_test.values, dtype=torch.float32).view(-1, 1)

class AvalancheNet(nn.Module):
    def __init__(self):
        super(AvalancheNet, self).__init__()
        self.layer1 = nn.Linear(X_train.shape[1], 16)
        self.layer2 = nn.Linear(16, 8)
        self.layer3 = nn.Linear(8, 1)
        self.relu = nn.ReLU()
        self.sigmoid = nn.Sigmoid()

    def forward(self, x):
        x = self.relu(self.layer1(x))
        x = self.relu(self.layer2(x))
        x = self.sigmoid(self.layer3(x))
        return x

model_pytorch = AvalancheNet()

criterion = nn.BCELoss() 
optimizer = optim.Adam(model_pytorch.parameters(), lr=0.01)

start_time = time.time()
epochs = 100
for epoch in range(epochs):
    model_pytorch.train()
    optimizer.zero_grad()
    outputs = model_pytorch(X_train_tensor)
    loss = criterion(outputs, y_train_tensor)
    loss.backward() 
    optimizer.step() 

pytorch_training_time = time.time() - start_time

model_pytorch.eval() 
with torch.no_grad(): 
    y_pred_tensor = model_pytorch(X_test_tensor)
    predicted_classes = (y_pred_tensor > 0.5).float()
    accuracy_pytorch = (predicted_classes == y_test_tensor).float().mean().item()

print(f"Code : ~25+ lignes (préparation des tenseurs, classe du modèle, boucle d'entraînement, etc.)")
print(f"Temps d'entraînement : {pytorch_training_time:.6f} secondes")
print(f"Précision sur le jeu de test : {accuracy_pytorch:.2f}")
