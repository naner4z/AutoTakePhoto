package com.example.zyh.autotakephoto;


import android.util.Log;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class HttpUtil {

    public static final String TAG = "HttpUtil";
    private static final String CHARSET = "utf-8";
    public static final String WRONG_USER_NAME_OR_PASSWORD_INFO = "false";

    public static void uploadData(String path, byte[] bytes) {

        int TIME_OUT = 1000 * 60 * 5;
        String boundary = UUID.randomUUID().toString();
        String lineEnd = "\r\n", prefix = "--";
        String content_type = "multipart/form-data";

        HttpURLConnection urlConnection = null;
        DataOutputStream dos;

        try {
            URL url = new URL(path);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(TIME_OUT);
            urlConnection.setReadTimeout(TIME_OUT);
            urlConnection.setRequestProperty("Charset", CHARSET);
            urlConnection.setRequestProperty("connection", "keep-alive");
            urlConnection.setRequestProperty("Content-Type", content_type + ";boundary=" + boundary);

            /*
            Http MIME ， 可以使用 wireshark 截取在浏览器上传时的数据， 以此来写自己的 MIME
             */

            //first boundary
            String firstBoundary = prefix + boundary + lineEnd;

//            String fileName = "" + "face.jpeg";
            //Encapsulated multipart part
            String contentDisposition = "Content-Disposition: form-data; name=\"file\"; filename=\"tface.jpeg\""
                    + lineEnd;
            String contentType = "Content-Type: image/jpeg" + lineEnd + lineEnd;

            //last boundary
            String lastBoundary = lineEnd + prefix + boundary + prefix + lineEnd;

            dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.write(firstBoundary.getBytes());
            dos.write(contentDisposition.getBytes());
            dos.write(contentType.getBytes());
            dos.write(bytes);
            dos.write(lastBoundary.getBytes());
            dos.flush();

//            Log.i(TAG, sb.toString() + "data..." + lastBoundary);
            Log.i(TAG, "length: " + bytes.length);

            /**
             * 可以上传了，不过在浏览器浏览的话会因为缓存的存在，所以没法将变化的信息立即反应。
             */

            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, "have response.");
            switch (responseCode) {
                case 200:
                    Log.i(TAG, "connect success.");
                    break;
                default:
                    Log.i(TAG, "connect failed.");
            }
        } catch (IOException e) {
            Log.i(TAG, "failed upload : " + e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static void uploadString(String path, String param) {
        int TIME_OUT = 1000 * 2;
        String boundary = UUID.randomUUID().toString();
        String lineEnd = "\r\n", prefix = "--";
        String content_type = "multipart/form-data";

        HttpURLConnection urlConnection = null;
        DataOutputStream dos;

        try {
            URL url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(TIME_OUT);
            urlConnection.setReadTimeout(TIME_OUT);
            urlConnection.setRequestProperty("Charset", CHARSET);
            urlConnection.setRequestProperty("connection", "keep-alive");
            urlConnection.setRequestProperty("Content-Type", content_type + ";boundary=" + boundary);

            dos = new DataOutputStream(urlConnection.getOutputStream());



            //first boundary
            String firstBoundary = prefix + boundary + lineEnd;
            //Encapsulated multipart part
            String aidPart = "Content-Disposition: form-data; name=\"aid\""
                    + lineEnd + lineEnd + param;
            //last boundary
            String lastBoundary = lineEnd + prefix + boundary + prefix + lineEnd;



            dos.write(firstBoundary.getBytes());
            dos.write(aidPart.getBytes());
            dos.write(lastBoundary.getBytes());
            dos.flush();

            int responseCode = urlConnection.getResponseCode();
            switch (responseCode) {
                case 200:
                    Log.i(TAG, "connect success.");
                    break;
                default:
                    Log.i(TAG, "connect failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


//    private static String sendDataToPath(String path, String name, String pd) throws NetworkErrorException{
//        int TIME_OUT = 1000 * 60;
//
//        HttpURLConnection urlConnection = null;
//        String boundary = UUID.randomUUID().toString();
//        String lineEnd = "\r\n", prefix = "--";
//        String content_type = "multipart/form-data";
//
//        InputStream in;
//
//        try {
//            URL url = new URL(path);
//            Log.i(TAG, "get url");
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            urlConnection.setUseCaches(false);
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setConnectTimeout(TIME_OUT);
//            urlConnection.setReadTimeout(TIME_OUT);
//            urlConnection.setRequestProperty("Charset", CHARSET);
//            urlConnection.setRequestProperty("connection", "keep-alive");
//            urlConnection.setRequestProperty("Content-Type", content_type + ";boundary=" + boundary);
//
//            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
//
//            //first boundary
//            String firstBoundary = prefix + boundary + lineEnd;
//            //Encapsulated multipart part
//            String namePart = "Content-Disposition: form-data; name=\"name\""
//                    + lineEnd + lineEnd;
//            //middle boundary
//            String midBoundary = lineEnd + firstBoundary;
//            //Encapsulated multipart part
//            String pdPart = "Content-Disposition: form-data; name=\"password\""
//                    + lineEnd + lineEnd;
//            //last boundary
//            String lastBoundary = lineEnd + prefix + boundary + prefix + lineEnd;
//
//
//
//            out.write(firstBoundary.getBytes());
//            out.write(namePart.getBytes());
//            out.write(name.getBytes());
//            out.write(midBoundary.getBytes());
//            out.write(pdPart.getBytes());
//            out.write(pd.getBytes());
//            out.write(lastBoundary.getBytes());
//            out.flush();
//
//
//            in = urlConnection.getInputStream();
//
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder builder = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                builder.append(line);
//            }
//
//            int response = urlConnection.getResponseCode();
//            if (response == 200) {
//                Log.i(TAG, "send data success.");
//            } else {
//                Log.i(TAG, "send data failed.");
//            }
//
//            return builder.toString();
//
//        } catch (IOException e) {
//            Log.i(TAG, "failed to open : " + e.toString());
//            throw new NetworkErrorException();
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//        }
//    }
//
//    public static String isUser(String path, String name, String pd) throws NetworkErrorException {
//        return sendDataToPath(path, name, pd);
//    }
//
//    public static String register(String path, String name, String pd) throws NetworkErrorException {
//        return sendDataToPath(path, name, pd);
//    }
}
