package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import vo.Book;
import vo.LendReturn;

import java.io.IOException;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LendReturnHttp {
    private static final String BASE_API_URL = "http://43.200.55.59:9090/LMS-Backend";

    public static boolean returnBooks(List<String> bookIds) throws Exception {
        String apiUrl = BASE_API_URL + "/return/books";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JSONArray jsonArray = new JSONArray(bookIds); // 리스트를 JSON 배열로 변환

        OutputStream os = conn.getOutputStream();
        os.write(jsonArray.toString().getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return Boolean.parseBoolean(response.toString().trim()); // "true" → true
        } else {
            throw new RuntimeException("반납 요청 실패 - 응답 코드: " + responseCode);
        }
    }

    public static List<LendReturn> searchLendBooks(String category, String keyword) throws Exception {
        List<LendReturn> list = new ArrayList<>();

        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String apiUrl = BASE_API_URL + "/return/searchLendBooks"
                + "?searchType=" + category + "&searchValue=" + encodedKeyword;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONArray arr = new JSONArray(sb.toString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            LendReturn lr = new LendReturn();
            lr.setLendIndex(obj.getInt("lendIndex"));
            lr.setMemId(obj.getString("memId"));
            lr.setBookId(obj.getString("bookId"));
            lr.setLendDate(obj.getString("lendDate"));
            lr.setReturnDate(obj.isNull("returnDate") ? null : obj.getString("returnDate"));
            lr.setReturnNY(obj.getInt("returnNY"));
            lr.setOverNY(obj.getInt("overNY"));

            // 🔥 여기 꼭 추가!
            lr.setBookTitle(obj.getString("bookTitle"));
            lr.setMemName(obj.getString("memName"));

            list.add(lr);
        }

        return list;
    }

    public static List<LendReturn> findAllNotReturn() throws Exception {
        List<LendReturn> result = new ArrayList<>();

        String apiUrl = BASE_API_URL + "/return/findAllNotReturn";  // ← URL 확인
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONArray arr = new JSONArray(response.toString());

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            LendReturn lend = new LendReturn();
            lend.setBookId(obj.getString("bookId"));
            lend.setBookTitle(obj.getString("bookTitle")); // ★ 누락되어 있던 경우
            lend.setMemName(obj.getString("memName"));     // ★ 누락되어 있던 경우
            lend.setLendDate(obj.getString("lendDate"));

            result.add(lend);
        }

        return result;
    }

    public static boolean lendBooks(String memId, List<String> bookIds) throws Exception {
        String apiUrl = BASE_API_URL + "/lend?memId=" + URLEncoder.encode(memId, "UTF-8");
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        // JSON 배열로 bookIds 작성
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(bookIds);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                return Boolean.parseBoolean(response.toString()); // "true" 또는 "false"
            }
        } else {
            System.err.println("대출 요청 실패. 응답 코드: " + responseCode);
            return false;
        }
    }

    private static List<LendReturn> fetchLendList(String apiUrl) throws Exception {
        List<LendReturn> lendList = new ArrayList<>();

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        // JSON 파싱
        JSONArray arr = new JSONArray(sb.toString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            LendReturn lendReturn = new LendReturn();
            lendReturn.setLendIndex(obj.getInt("lendIndex"));
            lendReturn.setBookId(obj.optString("bookId"));
            lendReturn.setMemId(obj.optString("memId"));
            lendReturn.setLendDate(obj.optString("lendDate"));
            lendReturn.setReturnDate(obj.optString("returnDate"));
            lendReturn.setReturnNY(obj.optInt("returnNY"));
            lendReturn.setOverNY(obj.optInt("overNY"));

            lendList.add(lendReturn);
        }

        return lendList;
    }
}
