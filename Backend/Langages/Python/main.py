# main.py

from fastapi import FastAPI
from typing import Union

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Bonjour, bienvenue sur le serveur FastAPI !"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}

@app.get("/utilisateurs/")
def read_users(skip: int = 0, limit: int = 10):
    fake_users_db = [{"user_name": "Alice"}, {"user_name": "Bob"}, {"user_name": "Charlie"}]
    return fake_users_db[skip : skip + limit]
