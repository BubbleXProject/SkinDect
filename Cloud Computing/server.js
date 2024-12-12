const express = require("express");
const dotenv = require("dotenv");

// Load environment variables from .env file (only for local development)
dotenv.config();

const app = express();
const port = process.env.PORT || 8080;
const apiKey = process.env.API_KEY; // API key from environment variables

// Sample news data
const newsData = {
  status: "ok",
  totalResults: 9873,
  articles: [
    {
      source: { id: null, name: "kompas.com" },
      author: "Ady Prawira Riandi Dan Tri Susanto Setiawan",
      title:
        "Anak Laki-laki Kanye West dan Kim Kardashian Didiagnosis Penyakit Kulit Langka",
      description:
        "Adapun penyakit kulit yang diidap adalah vitiligo, sebuah kelainan kulit langka yang menyebabkan hilangnya warna pada bercak-bercak kulit.",
      url: "https://www.kompas.com/hype/read/2024/07/22/095441266/anak-laki-laki-kanye-west-dan-kim-kardashian-didiagnosis-penyakit-kulit",
      urlToImage:
        "https://asset.kompas.com/crops/INnOV-mtvfU3qZY4r2cSiy4eSQg=/1x0:1480x986/1200x800/data/photo/2022/02/21/6212da1f04990.png",
      publishedAt: "22-07-2024T09:54Z",
    },
    {
      source: { id: null, name: "Antara" },
      author: "Antara",
      title: "Waspadai penyakit kulit yang berpotensi timbul saat musim hujan",
      description:
        "Musim hujan identik dengan lembap, dengan banjir. Nah itu biasanya penyakit kulit yang berhubungan untuk kondisi tersebut misalnya penyakit yang disebabkan oleh jamur kulit.",
      url: "https://www.antaranews.com/berita/3914505/waspadai-penyakit-kulit-yang-berpotensi-timbul-saat-musim-hujan",
      urlToImage:
        "https://cdn.antaranews.com/cache/1200x800/2023/09/30/IMG_20230930_155148.jpg.webp",
      publishedAt: "13-01-2024T19:48Z",
    },
    {
      source: { id: "null", name: "Antara" },
      author: " Oleh Ida Nurcahyani",
      title: "Psoriasis: pahami penyakit autoimun kulit dan upaya mengatasinya",
      description:
        "Psoriasis adalah penyakit kulit kronis yang mempengaruhi jutaan orang di seluruh dunia. Penyakit ini bisa menunjukkan gejala yang bervariasi pada setiap penderitanya, namun ada beberapa tanda yang sering muncul dan perlu diwaspadai sebagai bagian dari gejala umum psoriasis.",
      url: "https://www.antaranews.com/berita/4436365/psoriasis-pahami-penyakit-autoimun-kulit-dan-upaya-mengatasinya",
      urlToImage:
        "https://cdn.antaranews.com/cache/1200x800/2024/01/15/kulit.jpg.webp",
      publishedAt: " 1-11-2024",
    },
    {
      source: { id: "null", name: "HarianJogja" },
      author: " Enrich Samuel K.P Dan Maya Herawati",
      title: "Biduran Sering Muncul Saat Musim Hujan, Ini Cara Mengatasinya",
      description:
        "Udara dingin yang datang pada malam hari berpotensi menimbulkan alergi urtikaria atau yang biasa disebut dengan biduran.",
      url: "https://leisure.harianjogja.com/read/2024/10/17/508/1191921/biduran-sering-muncul-saat-musim-hujan-ini-cara-mengatasinya",
      urlToImage:
        "https://img.harianjogja.com/posts/2024/10/17/1191921/gatal-gatal.jpg",
      publishedAt: "17-10-2024T20:47Z",
    },
    {
      source: { id: "null", name: "detik.com" },
      author: "Aris Rivaldo",
      title:
        "Pengungsi Banjir Pandeglang Keluhkan Penyakit Kulit-Fasilitas Medis Terbatase",
      description:
        "Sebanyak 140 jiwa di Pagelaran, Kabupaten Pandeglang, mengungsi imbas terdampak banjir. Para pengungsi mengaku sudah terserang penyakit kulit, demam, hingga muntah-muntah.",
      url: "https://news.detik.com/berita/d-7675828/pengungsi-banjir-pandeglang-keluhkan-penyakit-kulit-fasilitas-medis-terbatas",
      urlToImage:
        "https://akcdn.detik.net.id/community/media/visual/2024/12/07/warga-terdampak-banjir-di-pandeglang-masih-bertahan-di-pengungsian-1_169.jpeg?w=700&q=90",
      publishedAt: "07-12-2024T18:51:Z",
    },
    {
      source: { id: "time", name: "Liputan6.com" },
      author: "Liputan6",
      title: "Ciri-Ciri Kadas Kurap: Kenali Gejala dan Cara Mengatasinya",
      description:
        "Kadas kurap, yang juga dikenal sebagai tinea corporis atau ringworm, merupakan infeksi jamur yang menyerang kulit.",
      url: "https://www.liputan6.com/feeds/read/5813087/ciri-ciri-kadas-kurap-kenali-gejala-dan-cara-mengatasinya",
      urlToImage:
        "https://cdn0-production-images-kly.akamaized.net/7xhxGPQOk88aUs25PPjTNiMwLd0=/640x360/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/5029280/original/084920900_1732947687-ciri-ciri-kadas-kurap.jpg",
      publishedAt: "06-12-2024T20:45Z",
    },
    {
      source: { id: "time", name: "Liputan6.com" },
      author: "Liputan6",
      title: "Ciri-Ciri Panu di Badan: Kenali Gejala dan Cara Mengatasinya",
      description:
        "Panu merupakan salah satu penyakit kulit yang cukup umum ditemui, terutama di daerah beriklim tropis seperti Indonesia.",
      url: "https://time.com/7200714/lara-trump-leaves-rnc-co-chair-senate-future-politics-speculation/",
      urlToImage:
        "https://cdn0-production-images-kly.akamaized.net/5Ou1gWE93PKpWTBmpFVORn1QGZo=/640x360/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/5029406/original/096607500_1732948091-ciri-ciri-panu-di-badan.jpg",
      publishedAt: "03-12-2024T15:45Z",
    },
    {
      source: { id: "time", name: "CNN" },
      author: "CNN Indonesia",
      title: "Awas, Kamu Bisa Kena 4 Penyakit Kulit Ini di Musim Hujan",
      description:
        "Replacing Marco Rubio in the Senate is something President Donald Trump’s daughter in law says she’d “seriously consider,” after stepping down as Republican National Committee co-chair.",
      url: "https://www.cnnindonesia.com/gaya-hidup/20241114090259-255-1166439/awas-kamu-bisa-kena-4-penyakit-kulit-ini-di-musim-hujan",
      urlToImage:
        "https://akcdn.detik.net.id/visual/2024/01/08/ilustrasi-kurap-di-kaki_169.jpeg?w=650&q=80",
      publishedAt: "19-11-2024T09:30Z",
    },
  ],
};

// Middleware to check API key
app.use((req, res, next) => {
  const clientApiKey = req.query.apiKey; // API key from query parameter

  if (!clientApiKey) {
    return res.status(400).json({
      status: "error",
      message: "API key is required",
    });
  }

  if (clientApiKey !== apiKey) {
    return res.status(403).json({
      status: "error",
      message: "Invalid API key",
    });
  }

  next();
});

// Root endpoint
app.get("/", (req, res) => {
  res.send("Selamat datang di API Berita Kesehatan Kulit!");
});

// News API endpoint
app.get("/api/news", (req, res) => {
  res.json(newsData);
});

// Start the server
app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});
