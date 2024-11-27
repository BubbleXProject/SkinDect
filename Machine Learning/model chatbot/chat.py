import json
import random
import numpy as np
import pickle
import tensorflow
from tensorflow import keras
from keras.models import load_model
from nltk_utils import tokenize, bag_of_words

# Muat file intents
with open("chatbot.json") as json_data:
    intents = json.load(json_data)

# Muat model yang telah dilatih
model = load_model("chat_model.h5", compile=False)

# Muat words dan classes dari file .pkl
with open("words.pkl", "rb") as f:
    all_words = pickle.load(f)

with open("classes.pkl", "rb") as f:
    tags = pickle.load(f)


def get_response(msg):
    # Tokenisasi dan buat bag_of_words untuk input pengguna
    sentence = tokenize(msg)
    X = bag_of_words(sentence, all_words)
    X = np.array([X])

    # Prediksi dengan model
    preds = model.predict(X)
    predicted_class = np.argmax(preds)

    tag = tags[predicted_class]

    # Cek probabilitas prediksi
    probs = preds[0]
    prob = probs[predicted_class]

    if prob > 0.75:
        for intent in intents["intents"]:
            if tag == intent["tag"]:
                return random.choice(intent["responses"])

    return "Maaf aku tidak paham maksudmu"
