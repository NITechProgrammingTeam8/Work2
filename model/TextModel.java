class TextModel {
    private int uuid;
    private String text;

    public TextModel(int uuid, String text) {
        this.uuid = uuid;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}