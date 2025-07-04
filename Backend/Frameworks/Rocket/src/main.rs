#[macro_use] extern crate rocket;

use rocket::serde::{json::Json, Serialize, Deserialize};

#[get("/")]
fn get_root() -> Json<serde_json::Value> {
    Json(serde_json::json!({
        "message": "Bonjour, bienvenue sur le serveur Rocket !"
    }))
}

#[derive(Serialize)]
#[serde(crate = "rocket::serde")]
struct ItemResponse {
    item_id: u32,
    q: Option<String>,
}

#[get("/items/<item_id>?<q>")]
fn get_item(item_id: u32, q: Option<String>) -> Json<ItemResponse> {
    Json(ItemResponse {
        item_id,
        q,
    })
}

#[derive(FromForm)]
struct Pagination {
    skip: Option<usize>,
    limit: Option<usize>,
}

#[derive(Serialize)]
#[serde(crate = "rocket::serde")]
struct User {
    user_name: String,
}

#[get("/utilisateurs?<pagination..>")]
fn get_users(pagination: Pagination) -> Json<Vec<User>> {
    let skip = pagination.skip.unwrap_or(0);
    let limit = pagination.limit.unwrap_or(10);

    let fake_users_db = vec![
        User { user_name: "Alice".to_string() },
        User { user_name: "Bob".to_string() },
        User { user_name: "Charlie".to_string() },
    ];

    let users: Vec<User> = fake_users_db.into_iter().skip(skip).take(limit).collect();

    Json(users)
}

#[launch]
fn rocket() -> _ {
    println!("Le serveur Rocket écoute sur http://127.0.0.1:8000");
    rocket::build()
        .mount("/", routes![get_root, get_item, get_users])
}
