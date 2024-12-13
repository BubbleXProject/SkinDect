from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
import tensorflow as tf
import numpy as np
from PIL import Image
import io
from pydantic import BaseModel

app = FastAPI()

# Allow CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Load the pre-trained model
model = tf.keras.models.load_model("model/model.h5", compile=False)

# Define the class labels
class_labels = ["cacar", "biduran", "eksim", "kudis", "kurap", "panu"]

recommendations = {
    "cacar": {
        "description": "Berdasarkan hasil pemindaian gambar, besar kemungkinan Anda terkena cacar air (varicella). Penyakit ini disebabkan oleh virus varicella-zoster dan sering menyerang anak-anak.",
        "recommendation": "Cacar air biasanya sembuh sendiri dalam 1-2 minggu. Anda bisa mengonsumsi obat pereda nyeri dan antihistamin untuk mengurangi gatal. Hindari menggaruk lenting untuk mencegah infeksi.",
    },
    "biduran": {
        "description": "Berdasarkan hasil pemindaian gambar, kemungkinan Anda mengalami biduran (urtikaria), yaitu reaksi alergi yang menyebabkan ruam atau bentol merah pada kulit.",
        "recommendation": "Biduran biasanya dapat diatasi dengan antihistamin untuk mengurangi rasa gatal dan peradangan. Jika parah, konsultasikan dengan dokter untuk perawatan lebih lanjut.",
    },
    "eksim": {
        "description": "Berdasarkan hasil pemindaian gambar, kemungkinan Anda terkena eksim (dermatitis atopik), yaitu peradangan kronis pada kulit.",
        "recommendation": "Eksim dapat dikelola dengan pelembap, krim kortikosteroid, dan menghindari pemicu iritasi. Jika parah, dokter mungkin akan meresepkan obat oral atau terapi cahaya.",
    },
    "kudis": {
        "description": "Berdasarkan hasil pemindaian gambar, besar kemungkinan Anda terkena penyakit kulit yaitu Kudis, atau scabies. Penyakit kulit ini disebabkan oleh tungau kecil bernama Sarcoptes scabiei. Tungau ini masuk ke lapisan kulit dan menyebabkan rasa gatal hebat, terutama di malam hari.",
        "recommendation": "Kudis dapat diobati dengan krim atau losion seperti permethrin atau ivermectin. Pastikan Anda juga mencuci semua pakaian dan seprai untuk mencegah penularan ulang.",
    },
    "kurap": {
        "description": "Berdasarkan hasil pemindaian gambar, besar kemungkinan Anda terkena kurap (ringworm). Kurap adalah infeksi jamur yang bisa muncul di kulit, rambut, atau kuku.",
        "recommendation": "Kurap dapat diobati dengan krim antijamur seperti clotrimazole atau terbinafine. Jika infeksi meluas, dokter mungkin akan meresepkan obat antijamur oral.",
    },
    "panu": {
        "description": "Berdasarkan hasil pemindaian gambar, besar kemungkinan Anda terkena penyakit kulit panu (tinea versicolor) yaitu infeksi kulit yang disebabkan oleh jamur.",
        "recommendation": "Panu bisa diobati dengan krim antijamur seperti miconazole atau clotrimazole, atau menggunakan obat oral jika infeksi cukup parah. Sebaiknya konsultasikan dengan dokter untuk perawatan yang tepat.",
    },
}


def preprocess_image(image: Image.Image, target_size: tuple = (150, 150)) -> np.ndarray:
    """
    Preprocess the input image to match the model's input requirements.
    :param image: The input image (PIL Image).
    :param target_size: The target size for the model (default: 150x150).
    :return: Preprocessed image as a NumPy array.
    """
    image = image.resize(target_size)  # Resize the image
    image = np.array(image) / 255.0  # Normalize to [0, 1]
    image = np.expand_dims(image, axis=0)  # Add batch dimension
    return image


class PredictionResponse(BaseModel):
    predicted_class: str
    confidence: float
    description: str
    recommendation: str


@app.get("/")
async def index():
    return JSONResponse(
        content={"status": "SUCCESS", "message": "Service is up"}, status_code=200
    )


@app.post("/predict", response_model=PredictionResponse)
async def predict(file: UploadFile = File(...)):
    """
    Predict the class of the uploaded image and provide recommendations.
    :return: JSON response with the predicted class label and recommendations.
    """
    if not file:
        raise HTTPException(status_code=400, detail="No file provided")

    try:
        # Open the image file
        image = Image.open(io.BytesIO(await file.read()))
        # Preprocess the image
        processed_image = preprocess_image(image)
        # Make predictions
        predictions = model.predict(processed_image)
        # Get the class label with the highest probability
        predicted_class = class_labels[np.argmax(predictions)]
        confidence = float(np.max(predictions))

        # Fetch the recommendation for the predicted class
        details = recommendations.get(predicted_class, {})
        description = details.get("description", "No description available.")
        recommendation = details.get("recommendation", "No recommendation available.")

        return PredictionResponse(
            predicted_class=predicted_class,
            confidence=confidence,
            description=description,
            recommendation=recommendation,
        )

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
