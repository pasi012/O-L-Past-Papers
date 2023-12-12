package inc.sltechnology.olpastpapers.Models;

public class PastPapersYearListModel {

    String paper_img;

    String paper_title;

    String paper_url;


    public PastPapersYearListModel() {

    }

    public PastPapersYearListModel(String paper_img, String paper_title, String paper_url) {
        this.paper_img = paper_img;
        this.paper_title = paper_title;
        this.paper_url = paper_url;
    }

    public String getPaper_img() {
        return paper_img;
    }

    public void setPaper_img(String paper_img) {
        this.paper_img = paper_img;
    }

    public String getPaper_title() {
        return paper_title;
    }

    public void setPaper_title(String paper_title) {
        this.paper_title = paper_title;
    }

    public String getPaper_url() {
        return paper_url;
    }

    public void setPaper_url(String paper_url) {
        this.paper_url = paper_url;
    }

}
