import requests
from flask import Flask, jsonify

from mod_user import mod_user

app= Flask(__name__)

app.register_blueprint(mod_user, url_prefix='/users')

baseURL = "http://localhost:3002/analysis"

@app.route('/', methods=['GET'])
def bonjour():
    return jsonify({'message':"Hello there"})

@app.route('/movies/most-saved', methods=['GET'])
def getMostSavedMovie():
    api_url = baseURL+"/movies/most-saved"
    response = requests.get(api_url)
    return response.json()


@app.route('/users/total', methods=['GET'])
def getTotalUsers():
    api_url = baseURL+"/users/total"
    response = requests.get(api_url)
    return response.json()

@app.route('/movies/total-saved', methods=['GET'])
def getTotalMovieSaved():
    api_url = baseURL+"/movies/total-saved"
    response = requests.get(api_url)
    return response.json()

@app.route('/movies/average-per-user', methods=['GET'])
def getAverageMovieByList():
    api_url = baseURL+"/users/total"
    response = requests.get(api_url)

    if response.status_code == 200:
        data = response.json()
        numberOfUsers  = data.get("data",{}).get("numberOfUsers")
    else:
        return  jsonify({'error': 'Failed to retrieve data from the external API'}), 500


    api_url = baseURL+"/movies/total-saved"
    response = requests.get(api_url)

    if response.status_code == 200:
        data = response.json()
        numberOfMovies  = data.get("data",{}).get("numberOfMovies")
    else:
        return  jsonify({'error': 'Failed to retrieve data from the external API'}), 500

    average_per_user = numberOfMovies/numberOfUsers;
    return  jsonify({"averageMoviePerUser" :  average_per_user})




if __name__ == '__main__':

    app.run(host='0.0.0.0',debug=True)