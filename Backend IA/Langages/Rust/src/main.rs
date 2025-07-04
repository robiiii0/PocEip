use axum::{
    extract::{Path, Query}, // Pour extraire les paramètres de l'URL
    routing::get, // Pour définir des routes GET
    response::Json, // Pour renvoyer des réponses JSON
    Router,
};
use serde::{Deserialize, Serialize}; // Pour la sérialisation/désérialisation JSON
use std::collections::HashMap;
use std::net::SocketAddr;

// Nous créons manuellement un runtime Tokio pour exécuter notre code asynchrone.
fn main() {
    // Crée un runtime Tokio.
    let rt = tokio::runtime::Runtime::new().unwrap();

    // Bloque le thread principal pour exécuter la logique asynchrone du serveur.
    rt.block_on(async {
        // Étape 2 : Créer le routeur de l'application
        // Le routeur associe des chemins d'URL (routes) à des fonctions (handlers).
        let app = Router::new()
            // Étape 3 : Définir la route racine ("/")
            .route("/", get(root_handler))
            // Étape 4 : Définir une route avec un paramètre de chemin
            .route("/items/:item_id", get(items_handler))
            // Étape 5 : Définir une route avec des paramètres de requête
            .route("/utilisateurs", get(users_handler));

        // Définir l'adresse et le port sur lesquels le serveur écoutera
        let addr = SocketAddr::from(([127, 0, 0, 1], 3000));
        println!("Le serveur Axum écoute sur http://{}", addr);

        // Lancer le serveur
        let listener = tokio::net::TcpListener::bind(addr).await.unwrap();
        axum::serve(listener, app).await.unwrap();
    });
}

// Handler pour la route racine "/"
async fn root_handler() -> Json<serde_json::Value> {
    // serde_json::json! est une macro pratique pour créer des objets JSON.
    println!("root_handler called");
    Json(serde_json::json!({ "message": "Bonjour, bienvenue sur le serveur Axum !" }))
}

// Structure pour la réponse du handler `items_handler`
// `Serialize` permet de convertir cette structure en JSON.
#[derive(Serialize)]
struct ItemResponse {
    item_id: u32,
    q: Option<String>,
}

// Handler pour la route "/items/:item_id"
async fn items_handler(
    Path(item_id): Path<u32>, // Extrait `item_id` du chemin, Axum le parse en u32
    Query(params): Query<HashMap<String, String>>, // Extrait tous les params de requête
) -> Json<ItemResponse> {
    println!("items_handler called with item_id: {}", item_id);
    // On cherche un paramètre 'q' dans la requête
    let q = params.get("q").cloned();

    Json(ItemResponse { item_id, q })
}

// Structure pour les paramètres de requête de `users_handler`
// `Deserialize` permet de créer cette structure à partir des paramètres d'URL.
#[derive(Deserialize)]
struct Pagination {
    skip: Option<usize>,
    limit: Option<usize>,
}

// Structure pour un utilisateur
#[derive(Serialize)]
struct User {
    user_name: String,
}

// Handler pour la route "/utilisateurs"
async fn users_handler(
    Query(pagination): Query<Pagination>
) -> Json<Vec<User>> {
    println!("users_handler called");
    let fake_users_db = vec![
        User { user_name: "Alice".to_string() },
        User { user_name: "Bob".to_string() },
        User { user_name: "Charlie".to_string() },
    ];

    // Utilise les valeurs de pagination, avec des valeurs par défaut si elles manquent.
    let skip = pagination.skip.unwrap_or(0);
    let limit = pagination.limit.unwrap_or(10);

    // Applique la pagination à notre "base de données" fictive.
    let users: Vec<User> = fake_users_db.into_iter().skip(skip).take(limit).collect();

    Json(users)
}
