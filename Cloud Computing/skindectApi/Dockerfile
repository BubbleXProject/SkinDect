# Gunakan image Python resmi
FROM python:3.9-slim

# Menyalin requirement file dan menginstal dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Menyalin kode aplikasi
COPY . /app
WORKDIR /app

# Menjalankan aplikasi
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8080"]
