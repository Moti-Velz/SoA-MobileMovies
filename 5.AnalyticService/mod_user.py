from flask import Blueprint, jsonify, request
import requests

mod_user = Blueprint('mod_user', __name__)

baseURL = "http://localhost:3002"

@mod_user.route('/', methods=['GET'])
def getUsers():
    api_url = baseURL+"/users"
    response = requests.get(api_url)
    return response.json()

@mod_user.route('/<int:userId>', methods=['GET'])
def getUser(userId):
    api_url = baseURL+"users/"+userId
    response = requests.get(api_url)
    return response.json()