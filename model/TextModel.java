class TextModel {
    private int uuid;
    private String text;

    public TextModel(int uuid, String text) {
        this.uuid = uuid;
        this.text = text;
    }

    // 表示用追加メソッド
    public int getUUID() {
    	return uuid;
    }

    public String getTEXT() {
    	return text;
    }
}