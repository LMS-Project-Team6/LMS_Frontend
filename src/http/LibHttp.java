package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import vo.Lib;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class LibHttp {
    private static final String BASE_API_URL = "http://43.200.55.59:9090/LMS-Backend/lib";

    // 로그인 요청
    public static Lib login(String libId, String libPw) throws Exception {
        String apiUrl = BASE_API_URL + "/login";

        URL url = new URL(apiUrl + "?libId=" + libId + "&libPw=" + libPw);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String response = in.lines().reduce("", (acc, line) -> acc + line);
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, Lib.class);
        } else {
            return null;
        }
    }
}
