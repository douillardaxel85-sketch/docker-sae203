const express = require("express");
const multer = require("multer");
const path = require("path");
const fs = require("fs");

const app = express();
const PORT = 3000;

// dossier upload
const uploadDir = "./uploads";
if (!fs.existsSync(uploadDir)) {
  fs.mkdirSync(uploadDir);
}

// config multer
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, uploadDir);
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + "-" + file.originalname);
  },
});

const upload = multer({ storage });

// route test
app.get("/", (req, res) => {
  res.send("API File Sharing OK");
});

// upload fichier
app.post("/upload", upload.single("file"), (req, res) => {
  res.json({
    message: "Fichier uploadé",
    file: req.file.filename,
  });
});

// liste fichiers
app.get("/files", (req, res) => {
  const files = fs.readdirSync(uploadDir);
  res.json(files);
});

// download fichier
app.get("/download/:name", (req, res) => {
  const filePath = path.join(uploadDir, req.params.name);
  res.download(filePath);
});

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});