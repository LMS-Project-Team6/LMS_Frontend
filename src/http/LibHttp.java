package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import vo.Lib;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

    // 회원가입 요청
    public static boolean register(Lib lib) throws Exception {
        String apiUrl = BASE_API_URL + "/save";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // 파라미터 구성
        String params =
                "libId=" + URLEncoder.encode(lib.getLibId(), "UTF-8") +
                "&libPw=" + URLEncoder.encode(lib.getLibPw(), "UTF-8") +
                "&libName=" + URLEncoder.encode(lib.getLibName(), "UTF-8") +
                "&libEmail=" + URLEncoder.encode(lib.getLibEmail(), "UTF-8") +
                "&libBirth=" + URLEncoder.encode(lib.getLibBirth(), "UTF-8") +
                "&libPNum=" + URLEncoder.encode(lib.getLibPNum(), "UTF-8") +
                "&adminNY=" + lib.getAdminNY() +
                "&applyNY=" + lib.getApplyNY();

        // 서버로 데이터 전송
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
            writer.write(params);
            writer.flush();
        }

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();
        System.out.println("서버 응답 코드: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 정상 응답일 경우 (200)
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("서버 응답 내용: " + response.toString());
            }
            return true;
        } else {
            // 오류 응답일 경우 (400, 500 등)
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"))) {
                String line;
                System.out.println("에러 응답 내용:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.out.println("에러 응답 본문을 읽는 중 예외 발생: " + e.getMessage());
            }
            return false;
        }
    }
}
