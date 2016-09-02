package ua.com.smiddle.task.core.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Added by A.Osadchuk on 04.08.2016 at 13:36.
 * Project: Task
 */
@Entity
@Table(name = "Table1")
public class Table1 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "X", nullable = false)
    private int x;
    @Column(name = "Y", nullable = false)
    private int y;
    @Column(name = "Z", nullable = true)
    private int z;


    //Constructors
    public Table1() {
    }

    public Table1(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Table1(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table1{");
        sb.append("id=").append(id);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append('}');
        return sb.toString();
    }
}
