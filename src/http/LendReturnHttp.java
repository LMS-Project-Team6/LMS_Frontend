package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import vo.Book;

import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LendReturnHttp {
    private static final String BASE_API_URL = "http://43.200.55.59:9090/LMS-Backend";

    public static List<Book> findAllNotReturn() throws Exception {
        String apiUrl = BASE_API_URL + "/findAllNotReturn";
        return fetchBookList(apiUrl);
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

    private static List<Book> fetchBookList(String apiUrl) throws Exception {
        List<Book> bookList = new ArrayList<>();

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

            Book book = new Book();
            book.setBookId(obj.optString("bookId"));
            book.setBookTitle(obj.optString("bookTitle"));
            book.setBookWriter(obj.optString("bookWriter"));
            book.setBookPublisher(obj.optString("bookPublisher"));
            book.setBookCNum(obj.optString("bookCNum"));
            book.setLendNY(obj.optInt("lendNY", 0));
            book.setCreatedDate(obj.optString("createdDate"));
            book.setModifiedDate(obj.optString("modifiedDate"));

            bookList.add(book);
        }

        return bookList;
    }햣
}
