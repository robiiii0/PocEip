use actix_web::{get, web, App, HttpServer, Responder, Result};
use serde::{Deserialize, Serialize};

#[get("/")]
async fn get_root() -> Result<impl Responder> {
    let response = serde_json::json!({
        "message": "Bonjour, bienvenue sur le serveur Actix Web !"
    });
    Ok(web::Json(response))
}

#[derive(Deserialize)]
struct ItemInfo {
    q: Option<String>,
}

#[get("/items/{item_id}")]
async fn get_item(
    path: web::Path<u32>,
    query: web::Query<ItemInfo>,
) -> Result<impl Responder> {
    println!("get_item called");
    let item_id = path.into_inner();
    let q_param = query.into_inner().q;

    let response = serde_json::json!({
        "item_id": item_id,
        "q": q_param
    });

    Ok(web::Json(response))
}

#[derive(Deserialize)]
struct Pagination {
    skip: Option<usize>,
    limit: Option<usize>,
}

#[derive(Serialize)]
struct User {
    user_name: String,
}

#[get("/utilisateurs")]
async fn get_users(query: web::Query<Pagination>) -> Result<impl Responder> {
    println!("get_users called");
    let pagination = query.into_inner();
    let skip = pagination.skip.unwrap_or(0);
    let limit = pagination.limit.unwrap_or(10);

    let fake_users_db = vec![
        User { user_name: "Alice".to_string() },
        User { user_name: "Bob".to_string() },
        User { user_name: "Charlie".to_string() },
    ];

    let users: Vec<User> = fake_users_db.into_iter().skip(skip).take(limit).collect();

    Ok(web::Json(users))
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let server_address = "127.0.0.1:8080";
    println!("Le serveur Actix-web écoute sur http://{}", server_address);

    HttpServer::new(|| {
        App::new()
            .service(get_root)
            .service(get_item)
            .service(get_users)
    })
        .bind(server_address)?
        .run()
        .await
}
