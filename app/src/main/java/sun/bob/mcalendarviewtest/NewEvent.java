package sun.bob.mcalendarviewtest;

import sun.bob.mcalendarview.vo.DateData;

public class NewEvent {
    private String startdate;
    private String enddate;
    private String title;
    private String type;
    private DateData start;
    private DateData end;

    public NewEvent() {
        this.startdate = "empty";
        this.enddate = "empty";
        this.type = "empty";
        this.title = "empty";
    }

    public NewEvent(String s, String e, String t, String ty) {
        this.startdate = s;
        this.enddate = e;
        this.type = ty;
        this.title = t;
    }


    //get + set

    public String getEnddate() {
        return enddate;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DateData getEnd() {
        return end;
    }

    public DateData getStart() {
        return start;
    }

    public void setEnd(DateData end) {
        this.end = end;
    }

    public void setStart(DateData start) {
        this.start = start;
    }
}
