# Menggunakan image node resmi
FROM node:14

# Mengatur direktori kerja
WORKDIR /usr/src/app

# Menyalin file package.json dan package-lock.json
COPY package*.json ./

# Menginstal dependensi
RUN npm install

# Menyalin semua file proyek
COPY . .

# Mengekspos port yang digunakan oleh aplikasi
EXPOSE 8080

# Menjalankan aplikasi
CMD [ "node", "server.js" ]
