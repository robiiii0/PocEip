# --- 1. Installation et chargement des packages ---
library(dplyr)
library(ggplot2)

# --- 2. Création de données fictives d'avalanche ---
set.seed(123)
n_observations <- 500

pente <- runif(n_observations, min = 20, max = 50)
neige_fraiche <- runif(n_observations, min = 0, max = 100)
temperature <- runif(n_observations, min = -20, max = 5)
vent <- runif(n_observations, min = 0, max = 80)
historique_avalanche <- sample(c(0, 1), n_observations, replace = TRUE, prob = c(0.8, 0.2))

prob_avalanche_raw <- 0.1
prob_avalanche_raw <- prob_avalanche_raw + (pente - 20) * 0.015
prob_avalanche_raw <- prob_avalanche_raw + neige_fraiche * 0.008
prob_avalanche_raw <- prob_avalanche_raw + (temperature + 10) * 0.005
prob_avalanche_raw <- prob_avalanche_raw + vent * 0.002
prob_avalanche_raw <- prob_avalanche_raw + historique_avalanche * 0.2

prob_avalanche <- pmin(1, pmax(0, prob_avalanche_raw))
avalanche_status <- ifelse(prob_avalanche > runif(n_observations, 0, 1), 1, 0)

data_avalanche <- data.frame(
  pente = pente,
  neige_fraiche = neige_fraiche,
  temperature = temperature,
  vent = vent,
  historique_avalanche = factor(historique_avalanche, levels = c(0, 1), labels = c("Non", "Oui")),
  avalanche = factor(avalanche_status, levels = c(0, 1), labels = c("Non", "Oui"))
)

print(head(data_avalanche))
print(summary(data_avalanche))

# --- 3. Préparation des données pour le modèle ---
set.seed(42)
train_index <- sample(nrow(data_avalanche), 0.8 * nrow(data_avalanche))

train_data <- data_avalanche[train_index, ]
test_data <- data_avalanche[-train_index, ]

# --- 4. Entraînement du modèle de Régression Logistique ---
model_avalanche <- glm(avalanche ~ ., data = train_data, family = binomial(link = "logit"))
print(summary(model_avalanche))

# --- 5. Prédiction sur l'ensemble de test ---
probabilities_test <- predict(model_avalanche, newdata = test_data, type = "response")
predictions_test <- ifelse(probabilities_test > 0.5, "Oui", "Non")
predictions_test <- factor(predictions_test, levels = c("Non", "Oui"))

# --- 6. Évaluation du modèle ---
confusion_matrix <- table(Prédictions = predictions_test, Vraies_Labels = test_data$avalanche)
print(confusion_matrix)

accuracy <- sum(diag(confusion_matrix)) / sum(confusion_matrix)
print(paste0("Exactitude du modèle : ", round(accuracy * 100, 2), "%"))

# --- 7. Exemple de prédiction pour de nouvelles données ---
new_data_high_risk <- data.frame(
  pente = 40,
  neige_fraiche = 60,
  temperature = -2,
  vent = 55,
  historique_avalanche = factor("Oui", levels = c("Non", "Oui"))
)
prob_high_risk <- predict(model_avalanche, newdata = new_data_high_risk, type = "response")
pred_high_risk <- ifelse(prob_high_risk > 0.5, "Oui", "Non")
print(paste0("Probabilité d'avalanche prédite (Haute risque) : ", round(prob_high_risk * 100, 2), "%"))
print(paste0("Prédiction (seuil 0.5) : ", pred_high_risk))

new_data_low_risk <- data.frame(
  pente = 25,
  neige_fraiche = 5,
  temperature = -15,
  vent = 10,
  historique_avalanche = factor("Non", levels = c("Non", "Oui"))
)
prob_low_risk <- predict(model_avalanche, newdata = new_data_low_risk, type = "response")
pred_low_risk <- ifelse(prob_low_risk > 0.5, "Oui", "Non")
print(paste0("Probabilité d'avalanche prédite (Basse risque) : ", round(prob_low_risk * 100, 2), "%"))
print(paste0("Prédiction (seuil 0.5) : ", pred_low_risk))