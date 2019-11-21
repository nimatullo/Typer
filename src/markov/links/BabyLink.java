package markov.links;
 class BabyLink {
    private BabyLink next;
    private String value;

    BabyLink(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }

    void setNext(BabyLink babyLink) {
        this.next = babyLink;
    }
}
