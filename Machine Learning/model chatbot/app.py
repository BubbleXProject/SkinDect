from flask import Flask, request, jsonify
from flask_cors import CORS
from chat import get_response

app = Flask(__name__)
CORS(app)


@app.route("/predict", methods=["POST"])
def predict():
    text = request.get_json().get("message")
    if text:
        response = get_response(text)
        message = {"answer": response}
        return jsonify(message)
    else:
        return jsonify({"error": "Invalid input"}), 400


if __name__ == "__main__":
    app.run(debug=True)
