import java.util.List;

class AccessTextFile {

    //追加メソッド
    void addData(String targetData) {

    }


    //削除メソッド
    void deleteData(String targetData) {
        dataList = readData("dataset.txt");
        List<String> deletedList;
        for(int i = 0; i < dataList.length(); i++) {
            if(!dataList[i].equals(targetData)) {
                deletedList.add(dataList[i]);
            }
        }
        updateData("dataset.txt", deleteList);
    }

    //読み込みメソッド
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

    //書き込みメソッド
    void updateData(String fileName, List<String> dataList, boolean appendFlag) {
        try {
            // 文字コードUTF-8を指定してPrintWriterオブジェクトを作る。
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName, appendFlag), "UTF-8"));
            for(int i = 0; i < dataList.size(); i++) {
                out.println(dataList.get(i));
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}