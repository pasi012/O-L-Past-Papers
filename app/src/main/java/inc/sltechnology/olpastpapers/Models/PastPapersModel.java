package inc.sltechnology.olpastpapers.Models;

public class PastPapersModel {

    private String img;
    private String title;
    private String subTitle;

    private String past_papers_year;

    public PastPapersModel() {

    }

    public PastPapersModel(String img, String title, String subTitle) {
        this.img = img;
        this.title = title;
        this.subTitle = subTitle;
    }

    public PastPapersModel(String past_papers_year) {
        this.past_papers_year = past_papers_year;
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

    public String getPast_papers_year() {
        return past_papers_year;
    }

    public void setPast_papers_year(String past_papers_year) {
        this.past_papers_year = past_papers_year;
    }

}
