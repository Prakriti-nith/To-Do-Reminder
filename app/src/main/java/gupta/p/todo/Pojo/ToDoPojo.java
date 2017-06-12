package gupta.p.todo.Pojo;

/**
 * Created by pgupta on 12/6/17.
 */

public class ToDoPojo {
    private String name,date,time,alpha;
    private int imageRes;

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    @Override
    public String toString() {
        return "ToDoPojo{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", alpha='" + alpha + '\'' +
                ", imageRes=" + imageRes +
                '}';
    }
}
