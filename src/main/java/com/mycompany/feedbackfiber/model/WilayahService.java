package com.mycompany.feedbackfiber.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** Mengambil data provinsi, kabupaten/kota, dan kecamatan dari API Wilayah Indonesia. */
public class WilayahService {
    private static final String BASE = "https://wilayah.web.id/api";
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15)).build();

    public record Wilayah(String kode, String nama) {
        @Override public String toString() { return nama; }
    }

    public List<Wilayah> getProvinsi() throws Exception {
        return ambil(BASE + "/provinces");
    }

    public List<Wilayah> getKabupaten(String kodeProvinsi) throws Exception {
        return ambil(BASE + "/regencies/" + kodeProvinsi);
    }

    public List<Wilayah> getKecamatan(String kodeKabupaten) throws Exception {
        return ambil(BASE + "/districts/" + kodeKabupaten);
    }

    private List<Wilayah> ambil(String url) throws Exception {
        List<Wilayah> hasil = new ArrayList<>();
        int page = 1;
        int totalPages;
        do {
            String separator = url.contains("?") ? "&" : "?";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + separator + "page=" + page + "&limit=1000"))
                    .timeout(Duration.ofSeconds(25)).header("Accept", "application/json").GET().build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalStateException("API wilayah mengembalikan HTTP " + response.statusCode());
            }
            JsonNode root = JSON.readTree(response.body());
            if (!"success".equalsIgnoreCase(root.path("status").asText())) {
                throw new IllegalStateException("Respons API wilayah tidak berhasil.");
            }
            JsonNode data = root.path("data");
            for (JsonNode item : data) {
                String kode = item.path("code").asText();
                String nama = item.path("name").asText();
                if (!kode.isBlank() && !nama.isBlank()) hasil.add(new Wilayah(kode, nama));
            }
            totalPages = Math.max(1, root.path("meta").path("totalPages").asInt(1));
            page++;
        } while (page <= totalPages);
        return hasil;
    }
}
