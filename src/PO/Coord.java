package PO;
public class Coord{
    private long px;
    private long py;

    public long getPx(){
        return this.px;
    }
    public long getPy(){
        return this.py;
    }
    public void setPx(){
        this.px = px;
    }
    public void setPy(){
        this.py = py;
    }
    public Coord(long px,long py){
        this.px = px;
        this.py = py;
    }
}
