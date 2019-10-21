import java.util.List;

class AccessTextFile {
    
    List<String> readData(String fileName) {
        List<String> data;
        try {
            // 文字コードUTF-8を指定してBufferedReaderオブジェクトを作る
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                data.add(line);
            }
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    void writeData(String fileName, String data) {
        try {
            // 文字コードUTF-8を指定してPrintWriterオブジェクトを作る。
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));

            out.println(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}