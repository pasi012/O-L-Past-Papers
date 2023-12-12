package inc.sltechnology.olpastpapers.Models;

public class MarkingsModel {

    private String img;

    private String title;

    private String subTitle;

    private String markings_year;

    public MarkingsModel() {
    }

    public MarkingsModel(String img, String title, String subTitle) {
        this.img = img;
        this.title = title;
        this.subTitle = subTitle;
    }

    public MarkingsModel(String markings_year) {
        this.markings_year = markings_year;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMarkings_year() {
        return markings_year;
    }

    public void setMarkings_year(String markings_year) {
        this.markings_year = markings_year;
    }

}
