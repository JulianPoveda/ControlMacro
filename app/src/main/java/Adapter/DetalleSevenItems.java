package Adapter;

/**
 * Created by JULIANEDUARDO on 26/03/2015.
 */
public class DetalleSevenItems {
    protected String 	item1;
    protected String 	item2;
    protected String 	item3;
    protected String 	item4;
    protected String    item5;
    protected String    item6;
    protected String    item7;
    protected long 		Id;

    public DetalleSevenItems(String _item1, String _item2, String _item3, String _item4, String _item5, String _item6, String _item7){
        super();
        this.item1 	= _item1;
        this.item2 	= _item2;
        this.item3 	= _item3;
        this.item4 	= _item4;
        this.item5  = _item5;
        this.item6  = _item6;
        this.item7  = _item7;
    }

    public String getItem1(){
        return this.item1;
    }

    public String getItem2(){
        return this.item2;
    }

    public String getItem3(){
        return this.item3;
    }

    public String getItem4(){
        return this.item4;
    }

    public String getItem5() {
        return item5;
    }

    public String getItem6() {
        return item6;
    }

    public String getItem7() {
        return item7;
    }

    public long getId(){
        return this.Id;
    }

    public void setItem1(String _item){
        this.item1 = _item;
    }

    public void setItem2(String _item){
        this.item2 = _item;
    }

    public void setItem3(String _item){
        this.item3 = _item;
    }

    public void setItem4(String _item){
        this.item4 = _item;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public void setId(long id){
        this.Id= id;
    }
}
