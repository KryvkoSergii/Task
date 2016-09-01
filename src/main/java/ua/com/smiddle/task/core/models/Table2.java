package ua.com.smiddle.task.core.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Added by A.Osadchuk on 04.08.2016 at 13:42.
 * Project: Task
 */
@Entity
@Table(name = "Table2")
public class Table2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "phonenumber", nullable = false, unique = true)
    private String phonenumber;
    @Column(name = "Ban_Start_Date", nullable = false)
    private Date ban_Start_Date;
    @Column(name = "Ban_Stop_Date", nullable = false)
    private Date ban_Stop_Date;
    @Column(name = "BanActive", nullable = false)
    private boolean banActive;


    public Table2() {
    }

    public Table2(String phonenumber, Date ban_Start_Date, Date ban_Stop_Date, boolean banActive) {
        this.phonenumber = phonenumber;
        this.ban_Start_Date = ban_Start_Date;
        this.ban_Stop_Date = ban_Stop_Date;
        this.banActive = banActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getBan_Start_Date() {
        return ban_Start_Date;
    }

    public void setBan_Start_Date(Date ban_Start_Date) {
        this.ban_Start_Date = ban_Start_Date;
    }

    public Date getBan_Stop_Date() {
        return ban_Stop_Date;
    }

    public void setBan_Stop_Date(Date ban_Stop_Date) {
        this.ban_Stop_Date = ban_Stop_Date;
    }

    public boolean isBanActive() {
        return banActive;
    }

    public void setBanActive(boolean banActive) {
        this.banActive = banActive;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table2{");
        sb.append("id=").append(id);
        sb.append(", phonenumber='").append(phonenumber).append('\'');
        sb.append(", ban_Start_Date=").append(ban_Start_Date);
        sb.append(", ban_Stop_Date=").append(ban_Stop_Date);
        sb.append(", banActive=").append(banActive);
        sb.append('}');
        return sb.toString();
    }
}
