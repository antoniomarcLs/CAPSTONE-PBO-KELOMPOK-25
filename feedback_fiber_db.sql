-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 02, 2026 at 08:15 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `feedback_fiber_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `jawaban`
--

CREATE TABLE `jawaban` (
  `id_jawaban` varchar(10) NOT NULL,
  `point` varchar(2) NOT NULL,
  `tanggal_pengisian` date NOT NULL,
  `id_user` varchar(10) NOT NULL,
  `id_pertanyaan` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `jawaban`
--

INSERT INTO `jawaban` (`id_jawaban`, `point`, `tanggal_pengisian`, `id_user`, `id_pertanyaan`) VALUES
('J001', '4', '2026-03-15', 'U002', 'P066'),
('J002', '3', '2026-03-15', 'U002', 'P067'),
('J003', '4', '2026-03-15', 'U002', 'P068'),
('J004', '3', '2026-03-15', 'U002', 'P069'),
('J005', '4', '2026-03-15', 'U002', 'P070'),
('J006', '3', '2026-03-16', 'U008', 'P066'),
('J007', '4', '2026-03-16', 'U008', 'P067'),
('J008', '5', '2026-03-16', 'U008', 'P068'),
('J009', '2', '2026-03-16', 'U008', 'P069'),
('J010', '3', '2026-03-16', 'U008', 'P070'),
('J011', '5', '2026-03-20', 'U001', 'P071'),
('J012', '4', '2026-03-20', 'U001', 'P072'),
('J013', '4', '2026-03-20', 'U001', 'P073'),
('J014', '3', '2026-03-20', 'U001', 'P074'),
('J015', '5', '2026-03-20', 'U001', 'P075'),
('J016', '4', '2026-03-21', 'U006', 'P071'),
('J017', '3', '2026-03-21', 'U006', 'P072'),
('J018', '3', '2026-03-21', 'U006', 'P073'),
('J019', '4', '2026-03-21', 'U006', 'P074'),
('J020', '4', '2026-03-21', 'U006', 'P075'),
('J021', '5', '2026-03-22', 'U012', 'P071'),
('J022', '5', '2026-03-22', 'U012', 'P072'),
('J023', '4', '2026-03-22', 'U012', 'P073'),
('J024', '4', '2026-03-22', 'U012', 'P074'),
('J025', '5', '2026-03-22', 'U012', 'P075');

-- --------------------------------------------------------

--
-- Table structure for table `kuesioner`
--

CREATE TABLE `kuesioner` (
  `id_kuesioner` varchar(10) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `deskripsi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kuesioner`
--

INSERT INTO `kuesioner` (`id_kuesioner`, `judul`, `deskripsi`) VALUES
('K001', 'Kepuasan Layanan Internet', 'Menilai kepuasan Anda terhadap layanan internet Fiber secara keseluruhan.'),
('K002', 'Kualitas Jaringan', 'Menilai kualitas jaringan, jangkauan, dan kemampuan koneksi dalam mendukung aktivitas Anda.'),
('K003', 'Kecepatan Internet', 'Menilai kesesuaian kecepatan internet dengan kebutuhan browsing, belajar, bekerja, dan hiburan.'),
('K004', 'Stabilitas Koneksi', 'Menilai kestabilan koneksi internet, termasuk frekuensi terputus dan gangguan saat digunakan.'),
('K005', 'Pelayanan Pelanggan', 'Menilai keramahan, kejelasan informasi, dan kemampuan petugas dalam membantu pelanggan.'),
('K006', 'Penanganan Gangguan', 'Menilai kecepatan respons dan penyelesaian masalah ketika layanan internet mengalami gangguan.'),
('K007', 'Kemudahan Aplikasi', 'Menilai kemudahan penggunaan aplikasi Fiber, termasuk navigasi dan akses ke fitur yang tersedia.'),
('K008', 'Kualitas Informasi', 'Menilai kejelasan dan kelengkapan informasi mengenai layanan, paket, tagihan, dan gangguan.'),
('K009', 'Keandalan Layanan', 'Menilai konsistensi layanan internet dalam mendukung aktivitas sehari-hari.'),
('K010', 'Kepuasan Pengguna', 'Menilai pengalaman penggunaan layanan Fiber dan kesesuaian layanan dengan harapan Anda.'),
('K011', 'Pengalaman Pelanggan', 'Menilai pengalaman Anda selama berlangganan, mulai dari penggunaan hingga interaksi dengan petugas.'),
('K012', 'Kualitas Bantuan Teknis', 'Menilai kemampuan petugas teknis dalam memahami masalah dan memberikan solusi yang tepat.'),
('K013', 'Paket Internet', 'Menilai kesesuaian pilihan paket, manfaat, dan harga dengan kebutuhan pelanggan.'),
('K014', 'Komunikasi Layanan', 'Menilai kejelasan dan ketepatan waktu penyampaian informasi mengenai perubahan layanan dan gangguan.'),
('K015', 'Evaluasi Umum FIBER', 'Memberikan penilaian umum terhadap kualitas layanan Fiber serta aspek yang perlu ditingkatkan.');

-- --------------------------------------------------------

--
-- Table structure for table `pertanyaan`
--

CREATE TABLE `pertanyaan` (
  `id_pertanyaan` varchar(10) NOT NULL,
  `isi_pertanyaan` varchar(255) NOT NULL,
  `id_kuesioner` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pertanyaan`
--

INSERT INTO `pertanyaan` (`id_pertanyaan`, `isi_pertanyaan`, `id_kuesioner`) VALUES
('P001', 'Seberapa puas Anda terhadap layanan internet Fiber secara keseluruhan?', 'K001'),
('P002', 'Seberapa sesuai layanan Fiber dengan kebutuhan internet Anda?', 'K001'),
('P003', 'Seberapa sesuai layanan yang diterima dengan harapan Anda?', 'K001'),
('P004', 'Seberapa puas Anda terhadap manfaat layanan dibandingkan biaya yang dibayar?', 'K001'),
('P005', 'Seberapa besar kemungkinan Anda tetap menggunakan layanan Fiber?', 'K001'),
('P006', 'Bagaimana penilaian Anda terhadap kualitas jaringan Fiber di lokasi Anda?', 'K002'),
('P007', 'Seberapa baik jangkauan jaringan dalam mendukung aktivitas internet Anda?', 'K002'),
('P008', 'Seberapa baik kualitas koneksi saat beberapa perangkat digunakan bersamaan?', 'K002'),
('P009', 'Seberapa baik kualitas jaringan saat digunakan untuk aktivitas daring?', 'K002'),
('P010', 'Seberapa puas Anda terhadap kualitas jaringan secara keseluruhan?', 'K002'),
('P011', 'Seberapa sesuai kecepatan internet dengan kebutuhan sehari-hari Anda?', 'K003'),
('P012', 'Seberapa cepat halaman web terbuka saat menggunakan layanan Fiber?', 'K003'),
('P013', 'Seberapa lancar video dapat diputar melalui koneksi internet Anda?', 'K003'),
('P014', 'Seberapa memadai kecepatan internet untuk mengunduh atau mengunggah berkas?', 'K003'),
('P015', 'Seberapa puas Anda terhadap kecepatan internet yang diterima?', 'K003'),
('P016', 'Seberapa stabil koneksi internet saat digunakan sehari-hari?', 'K004'),
('P017', 'Seberapa jarang koneksi internet terputus ketika sedang digunakan?', 'K004'),
('P018', 'Seberapa konsisten koneksi internet pada waktu penggunaan yang berbeda?', 'K004'),
('P019', 'Seberapa stabil koneksi ketika melakukan panggilan video atau rapat daring?', 'K004'),
('P020', 'Seberapa puas Anda terhadap kestabilan koneksi Fiber?', 'K004'),
('P021', 'Seberapa ramah petugas saat melayani pertanyaan atau keluhan Anda?', 'K005'),
('P022', 'Seberapa jelas penjelasan yang diberikan oleh petugas layanan pelanggan?', 'K005'),
('P023', 'Seberapa mudah Anda menghubungi layanan pelanggan ketika membutuhkan bantuan?', 'K005'),
('P024', 'Seberapa baik petugas memahami kebutuhan atau kendala yang Anda sampaikan?', 'K005'),
('P025', 'Seberapa puas Anda terhadap pelayanan pelanggan Fiber?', 'K005'),
('P026', 'Seberapa cepat laporan gangguan Anda mendapatkan respons?', 'K006'),
('P027', 'Seberapa cepat gangguan internet yang Anda alami diselesaikan?', 'K006'),
('P028', 'Seberapa jelas informasi mengenai perkembangan penanganan gangguan?', 'K006'),
('P029', 'Seberapa sesuai solusi yang diberikan dengan masalah yang Anda alami?', 'K006'),
('P030', 'Seberapa puas Anda terhadap penanganan gangguan oleh Fiber?', 'K006'),
('P031', 'Seberapa mudah Anda memahami tampilan aplikasi Fiber?', 'K007'),
('P032', 'Seberapa mudah Anda menemukan fitur yang dibutuhkan dalam aplikasi?', 'K007'),
('P033', 'Seberapa mudah Anda menggunakan fitur aplikasi tanpa bantuan orang lain?', 'K007'),
('P034', 'Seberapa nyaman navigasi antarmenu dalam aplikasi Fiber?', 'K007'),
('P035', 'Seberapa puas Anda terhadap kemudahan penggunaan aplikasi Fiber?', 'K007'),
('P036', 'Seberapa jelas informasi mengenai paket internet yang tersedia?', 'K008'),
('P037', 'Seberapa mudah Anda memahami informasi tagihan layanan?', 'K008'),
('P038', 'Seberapa lengkap informasi yang diberikan saat terjadi gangguan?', 'K008'),
('P039', 'Seberapa mudah Anda menemukan informasi layanan yang dibutuhkan?', 'K008'),
('P040', 'Seberapa puas Anda terhadap kualitas informasi dari Fiber?', 'K008'),
('P041', 'Seberapa dapat diandalkan layanan Fiber untuk aktivitas harian Anda?', 'K009'),
('P042', 'Seberapa konsisten layanan internet tersedia saat dibutuhkan?', 'K009'),
('P043', 'Seberapa baik layanan mendukung pekerjaan atau pembelajaran daring?', 'K009'),
('P044', 'Seberapa jarang kendala layanan menghambat aktivitas internet Anda?', 'K009'),
('P045', 'Seberapa puas Anda terhadap keandalan layanan Fiber?', 'K009'),
('P046', 'Seberapa puas Anda terhadap pengalaman menggunakan layanan Fiber?', 'K010'),
('P047', 'Seberapa baik layanan Fiber memenuhi harapan Anda sebagai pengguna?', 'K010'),
('P048', 'Seberapa nyaman Anda menggunakan layanan Fiber untuk aktivitas sehari-hari?', 'K010'),
('P049', 'Seberapa besar kemungkinan Anda merekomendasikan Fiber kepada orang lain?', 'K010'),
('P050', 'Seberapa puas Anda terhadap layanan Fiber secara umum?', 'K010'),
('P051', 'Seberapa mudah proses awal penggunaan layanan Fiber bagi Anda?', 'K011'),
('P052', 'Seberapa nyaman pengalaman Anda selama berlangganan Fiber?', 'K011'),
('P053', 'Seberapa baik pengalaman Anda saat membutuhkan bantuan layanan?', 'K011'),
('P054', 'Seberapa sesuai pengalaman penggunaan layanan dengan harapan Anda?', 'K011'),
('P055', 'Seberapa puas Anda terhadap pengalaman sebagai pelanggan Fiber?', 'K011'),
('P056', 'Seberapa baik petugas teknis memahami masalah yang Anda laporkan?', 'K012'),
('P057', 'Seberapa jelas petugas menjelaskan penyebab gangguan yang terjadi?', 'K012'),
('P058', 'Seberapa tepat solusi teknis yang diberikan untuk masalah Anda?', 'K012'),
('P059', 'Seberapa profesional petugas teknis saat menangani kendala?', 'K012'),
('P060', 'Seberapa puas Anda terhadap bantuan teknis yang diberikan?', 'K012'),
('P061', 'Seberapa sesuai pilihan paket internet dengan kebutuhan Anda?', 'K013'),
('P062', 'Seberapa sesuai harga paket dengan manfaat yang Anda terima?', 'K013'),
('P063', 'Seberapa jelas informasi mengenai manfaat setiap paket internet?', 'K013'),
('P064', 'Seberapa mudah Anda memilih paket yang sesuai dengan kebutuhan?', 'K013'),
('P065', 'Seberapa puas Anda terhadap pilihan paket internet Fiber?', 'K013'),
('P066', 'Seberapa jelas informasi mengenai perubahan layanan yang disampaikan?', 'K014'),
('P067', 'Seberapa tepat waktu informasi gangguan disampaikan kepada pelanggan?', 'K014'),
('P068', 'Seberapa mudah Anda memahami pengumuman atau pemberitahuan layanan?', 'K014'),
('P069', 'Seberapa memadai informasi mengenai tindak lanjut gangguan yang diberikan?', 'K014'),
('P070', 'Seberapa puas Anda terhadap komunikasi layanan Fiber?', 'K014'),
('P071', 'Seberapa puas Anda terhadap kualitas layanan Fiber secara keseluruhan?', 'K015'),
('P072', 'Seberapa baik Fiber memenuhi kebutuhan internet Anda?', 'K015'),
('P073', 'Seberapa baik Fiber menangani keluhan pelanggan?', 'K015'),
('P074', 'Seberapa sesuai layanan Fiber dengan biaya yang Anda keluarkan?', 'K015'),
('P075', 'Seberapa besar kemungkinan Anda merekomendasikan layanan Fiber?', 'K015');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id_review` varchar(10) NOT NULL,
  `anonim` tinyint(1) DEFAULT '0',
  `review` text NOT NULL,
  `upvote` int NOT NULL DEFAULT '0',
  `id_user` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id_review`, `anonim`, `review`, `upvote`, `id_user`) VALUES
('R001', 0, 'Pelayanan cukup baik dan informasi paket mudah dipahami.', 8, 'U001'),
('R002', 0, 'Koneksi internet cukup stabil untuk belajar dan mengerjakan tugas.', 12, 'U002'),
('R003', 1, 'Kecepatan internet sudah sesuai untuk kebutuhan sehari-hari.', 15, 'U003'),
('R004', 0, 'Semoga penanganan gangguan dapat dilakukan lebih cepat.', 3, 'U004'),
('R005', 0, 'Petugas layanan pelanggan membantu menjelaskan kendala dengan baik.', 10, 'U005'),
('R006', 1, 'Aplikasi cukup mudah digunakan, tetapi beberapa menu masih bisa diperjelas.', 6, 'U006'),
('R007', 0, 'Pilihan paket internet cukup beragam dan informasinya mudah ditemukan.', 9, 'U007'),
('R008', 0, 'Secara umum kualitas layanan sudah baik untuk aktivitas harian.', 7, 'U008'),
('R009', 1, 'Kestabilan jaringan pada malam hari masih perlu ditingkatkan.', 14, 'U009'),
('R010', 0, 'Bantuan teknis cukup membantu ketika terjadi masalah koneksi.', 5, 'U010'),
('R011', 0, 'Pengalaman menggunakan layanan cukup memuaskan sejauh ini.', 11, 'U011'),
('R012', 1, 'Informasi mengenai status gangguan sebaiknya diberikan secara berkala.', 4, 'U012'),
('R013', 0, 'Pilihan paket cukup baik, tetapi kesesuaian harga dan manfaat perlu diperhatikan.', 8, 'U013'),
('R014', 0, 'Respons layanan pelanggan sudah cukup baik dan informatif.', 13, 'U014'),
('R015', 1, 'Saya cukup puas dengan layanan secara keseluruhan dan berharap kualitasnya tetap terjaga.', 16, 'U015');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(10) NOT NULL,
  `nama_pengguna` varchar(100) NOT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `domisili` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama_pengguna`, `tanggal_lahir`, `gender`, `domisili`, `password`, `role`) VALUES
('U001', 'Alya Putri', '2004-01-12', 'Perempuan', 'Samarinda Ulu, Samarinda, Kalimantan Timur', 'Alyafiber.27', 'user'),
('U002', 'Bima Pratama', '2003-02-18', 'Laki-laki', 'Balikpapan Selatan, Balikpapan, Kalimantan Timur', 'Bimasukavalorant9', 'user'),
('U003', 'Citra Lestari', '2004-03-22', 'Perempuan', 'Bontang Utara, Bontang, Kalimantan Timur', 'Citra_Moon!42', 'user'),
('U004', 'Dimas Saputra', '2002-04-05', 'Laki-laki', 'Samarinda Kota, Samarinda, Kalimantan Timur', 'Dimas!Ngopi_83', 'user'),
('U005', 'Eka Wulandari', '2003-05-14', 'Perempuan', 'Tenggarong, Kutai Kartanegara, Kalimantan Timur', 'EkaBloom#516', 'user'),
('U006', 'Farhan Akbar', '2004-06-09', 'Laki-laki', 'Balikpapan Utara, Balikpapan, Kalimantan Timur', 'Farhan.exe!28', 'user'),
('U007', 'Gita Maharani', '2003-07-17', 'Perempuan', 'Samarinda Ilir, Samarinda, Kalimantan Timur', 'GitaVibes#731', 'user'),
('U008', 'Hafiz Ramadhan', '2002-08-21', 'Laki-laki', 'Bontang Selatan, Bontang, Kalimantan Timur', 'HafizChill!64', 'user'),
('U009', 'Intan Permata', '2004-09-11', 'Perempuan', 'Samarinda Seberang, Samarinda, Kalimantan Timur', 'Intan.Senja#58', 'user'),
('U010', 'Joko Firmansyah', '2003-10-03', 'Laki-laki', 'Tenggarong Seberang, Kutai Kartanegara, Kalimantan Timur', 'JokoPixel!914', 'user'),
('U011', 'Kartika Sari', '2004-11-19', 'Perempuan', 'Balikpapan Tengah, Balikpapan, Kalimantan Timur', 'KartikaBloom#26', 'user'),
('U012', 'Lukman Hakim', '2002-12-24', 'Laki-laki', 'Samarinda Utara, Samarinda, Kalimantan Timur', 'LukmanMode!407', 'user'),
('U013', 'Maya Anggraini', '2003-01-30', 'Perempuan', 'Bontang Barat, Bontang, Kalimantan Timur', 'MayaCloud#825', 'user'),
('U014', 'Nanda Kurniawan', '2004-02-08', 'Laki-laki', 'Balikpapan Kota, Balikpapan, Kalimantan Timur', 'Nanda.bpp619', 'user'),
('U015', 'Olivia Safitri', '2003-03-16', 'Perempuan', 'Samarinda Palaran, Samarinda, Kalimantan Timur', 'OliviaDream!352', 'user'),
('U016', 'Admin Feedback', NULL, NULL, NULL, 'AdminFiber28', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `user_account`
--

CREATE TABLE `user_account` (
  `id_user` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_account`
--

INSERT INTO `user_account` (`id_user`, `username`) VALUES
('U016', 'admin_feedback'),
('U001', 'alyaputri.reads'),
('U002', 'bimz.exe'),
('U003', 'citraafterglow'),
('U004', 'dmsaputra_02'),
('U005', 'ekawulann'),
('U006', 'farhan.playz'),
('U007', 'gitamhrn'),
('U008', 'hafizramz'),
('U009', 'intan.pixels'),
('U010', 'jokofirmans'),
('U011', 'kartikaspace'),
('U012', 'lukmanhakim_'),
('U013', 'mayangraini'),
('U014', 'nandakrnwn'),
('U015', 'oliv.saf');

-- --------------------------------------------------------

--
-- Table structure for table `user_email`
--

CREATE TABLE `user_email` (
  `id_user` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_email`
--

INSERT INTO `user_email` (`id_user`, `email`) VALUES
('U016', 'admin.feedback@fiber.com'),
('U001', 'alya.putri24@gmail.com'),
('U002', 'bima.pratama03@gmail.com'),
('U003', 'citra.lestari04@gmail.com'),
('U004', 'dimas.saputra02@gmail.com'),
('U005', 'eka.wulandari03@gmail.com'),
('U006', 'farhan.akbar04@gmail.com'),
('U007', 'gita.maharani03@gmail.com'),
('U008', 'hafiz.ramadhan02@gmail.com'),
('U009', 'intan.permata04@gmail.com'),
('U010', 'joko.firmansyah03@gmail.com'),
('U011', 'kartika.sari04@gmail.com'),
('U012', 'lukman.hakim02@gmail.com'),
('U013', 'maya.anggraini03@gmail.com'),
('U014', 'nanda.kurniawan04@gmail.com'),
('U015', 'olivia.safitri03@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `user_phone`
--

CREATE TABLE `user_phone` (
  `id_user` varchar(10) NOT NULL,
  `nomor_hp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_phone`
--

INSERT INTO `user_phone` (`id_user`, `nomor_hp`) VALUES
('U005', '081211223344'),
('U011', '081212345679'),
('U009', '081213445566'),
('U013', '081214556677'),
('U003', '081214567821'),
('U007', '081215667788'),
('U006', '081216543219'),
('U002', '081216729814'),
('U015', '081216778899'),
('U012', '081217654321'),
('U010', '081217891234'),
('U008', '081218314627'),
('U001', '081218341205'),
('U014', '081219182736'),
('U004', '081219451673');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jawaban`
--
ALTER TABLE `jawaban`
  ADD PRIMARY KEY (`id_jawaban`),
  ADD KEY `fk_jawaban_user` (`id_user`),
  ADD KEY `fk_jawaban_pertanyaan` (`id_pertanyaan`);

--
-- Indexes for table `kuesioner`
--
ALTER TABLE `kuesioner`
  ADD PRIMARY KEY (`id_kuesioner`);

--
-- Indexes for table `pertanyaan`
--
ALTER TABLE `pertanyaan`
  ADD PRIMARY KEY (`id_pertanyaan`),
  ADD KEY `id_kuesioner` (`id_kuesioner`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id_review`),
  ADD KEY `fk_review_user` (`id_user`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `user_account`
--
ALTER TABLE `user_account`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `user_email`
--
ALTER TABLE `user_email`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_phone`
--
ALTER TABLE `user_phone`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `nomor_hp` (`nomor_hp`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `jawaban`
--
ALTER TABLE `jawaban`
  ADD CONSTRAINT `fk_jawaban_pertanyaan` FOREIGN KEY (`id_pertanyaan`) REFERENCES `pertanyaan` (`id_pertanyaan`),
  ADD CONSTRAINT `fk_jawaban_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `pertanyaan`
--
ALTER TABLE `pertanyaan`
  ADD CONSTRAINT `pertanyaan_ibfk_1` FOREIGN KEY (`id_kuesioner`) REFERENCES `kuesioner` (`id_kuesioner`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `fk_review_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `user_account`
--
ALTER TABLE `user_account`
  ADD CONSTRAINT `user_account_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `user_email`
--
ALTER TABLE `user_email`
  ADD CONSTRAINT `user_email_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `user_phone`
--
ALTER TABLE `user_phone`
  ADD CONSTRAINT `user_phone_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
