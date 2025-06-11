package vo;

public class Book {
    private String bookId;
    private String bookTitle;
    private String bookWriter;
    private String bookPublisher;
    private String bookCNum;
    private String bookIntrd;
    private int lendNY;
    private String createdDate;
    private String modifiedDate;

    // 기본 생성자
    public Book() {}

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookCNum() {
        return bookCNum;
    }

    public void setBookCNum(String bookCNum) {
        this.bookCNum = bookCNum;
    }

    public String getBookIntrd() {
        return bookIntrd;
    }

    public void setBookIntrd(String bookIntrd) {
        this.bookIntrd = bookIntrd;
    }

    public int getLendNY() {
        return lendNY;
    }

    public void setLendNY(int lendNY) {
        this.lendNY = lendNY;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
