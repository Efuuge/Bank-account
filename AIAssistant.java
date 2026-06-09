import java.io.*;
import java.net.*;
import java.util.*;

public class AIAssistant {
    private List<String[]> history = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 14;

    private String loadApiKey() {
        try (BufferedReader r = new BufferedReader(new FileReader("api_key.txt"))) {
            return r.readLine().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public void startConversation(Scanner scanner) {
        String apiKey = loadApiKey();
        if (apiKey == null) {
            System.out.println(Lang.get("ai_key_missing"));
            return;
        }

        System.out.println(Lang.get("ai_greeting"));
        System.out.println();

        while (true) {
            System.out.print(Lang.get("ai_you"));
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("выход") || input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("chiqish")) {
                break;
            }
            if (input.isEmpty()) continue;

            if (history.size() >= MAX_HISTORY_SIZE) {
                history.remove(0);
                history.remove(0);
            }

            history.add(new String[]{"user", input});
            System.out.print(Lang.get("ai_prefix"));
            String response = sendMessage(apiKey);
            history.add(new String[]{"assistant", response});
            System.out.println(response);
            System.out.println();
        }
    }

    private String sendMessage(String apiKey) {
        try {
            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("{\"model\":\"llama3-8b-8192\",\"messages\":[");
            sb.append("{\"role\":\"system\",\"content\":\"You are a helpful financial assistant inside a banking app. Answer short and precisely.\"}");

            for (String[] msg : history) {
                sb.append(",{\"role\":\"").append(msg[0]).append("\",\"content\":\"").append(escape(msg[1])).append("\"}");
            }
            sb.append("]}");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(sb.toString().getBytes("UTF-8"));
            }

            int code = conn.getResponseCode();
            StringBuilder resp = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    code == 200 ? conn.getInputStream() : conn.getErrorStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resp.append(line);
                }
            }

            if (code != 200) return "Error " + code + ": " + resp;
            return extractText(resp.toString());

        } catch (Exception e) {
            return "Connection error: " + e.getMessage();
        }
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private String extractText(String json) {
        String marker = "\"content\":\"";
        int start = json.indexOf(marker);
        if (start == -1) return "Could not parse response";
        start += marker.length();
        StringBuilder result = new StringBuilder();
        boolean esc = false;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (esc) {
                if (c == 'n') result.append('\n');
                else if (c == 't') result.append('\t');
                else if (c == '"') result.append('"');
                else if (c == '\\') result.append('\\');
                else result.append(c);
                esc = false;
            } else if (c == '\\') {
                esc = true;
            } else if (c == '"') {
                break;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
