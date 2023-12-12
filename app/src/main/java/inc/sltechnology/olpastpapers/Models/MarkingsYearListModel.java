package inc.sltechnology.olpastpapers.Models;

public class MarkingsYearListModel {

    String marking_img;

    String marking_title;

    String marking_url;

    public MarkingsYearListModel() {
    }

    public MarkingsYearListModel(String marking_img, String marking_title, String marking_url) {
        this.marking_img = marking_img;
        this.marking_title = marking_title;
        this.marking_url = marking_url;
    }

    public String getMarking_img() {
        return marking_img;
    }

    public void setMarking_img(String marking_img) {
        this.marking_img = marking_img;
    }

    public String getMarking_title() {
        return marking_title;
    }

    public void setMarking_title(String marking_title) {
        this.marking_title = marking_title;
    }

    public String getMarking_url() {
        return marking_url;
    }

    public void setMarking_url(String marking_url) {
        this.marking_url = marking_url;
    }
}
